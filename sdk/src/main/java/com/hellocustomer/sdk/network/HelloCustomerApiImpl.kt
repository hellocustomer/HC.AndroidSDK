package com.hellocustomer.sdk.network

import com.hellocustomer.sdk.exception.HelloCustomerHttpException
import com.hellocustomer.sdk.exception.UnauthorizedException
import com.hellocustomer.sdk.logger.Logger
import com.hellocustomer.sdk.network.dto.LanguageDesignDto
import com.hellocustomer.sdk.network.dto.TouchpointDto
import com.squareup.moshi.JsonAdapter
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

internal class HelloCustomerApiImpl(
    private val touchpointAdapter: JsonAdapter<Collection<TouchpointDto>>,
    private val designAdapter: JsonAdapter<Collection<LanguageDesignDto>>,
    private val logger: Logger
) : HelloCustomerApi {

    private val apiErrorParser = ApiErrorParser()

    override fun getQuestions(builder: UrlBuilder): Result<Collection<TouchpointDto>> =
        callGet(builder, touchpointAdapter)

    override fun getLanguageDesigns(builder: UrlBuilder): Result<Collection<LanguageDesignDto>> =
        callGet(builder, designAdapter)

    private fun <T> Result<T>.mapError(): Result<T> {
        return when {
            isSuccess -> this
            isFailure -> Result.failure(
                exception = requireNotNull(this.exceptionOrNull())
            )
            else -> throw IllegalStateException("Illegal state of the internal API. Result couldn't be success and failure simultaneously or neither of them.")
        }
    }

    private fun <T : Any> callGet(builder: UrlBuilder, adapter: JsonAdapter<T>): Result<T> =
        call(URL(builder.buildUrl()), "GET", builder.buildHeaders())
            .mapCatching { response -> adapter.requireFromJson(response.content) }

    private fun call(url: URL, requestMethod: String, headers: Map<String, String>): Result<Response> {
        val result = kotlin.runCatching {
            logD("--> $requestMethod ${url.toURI()}")
            headers.forEach { (key, value) -> logD("--> $key: $value") }

            val connection: HttpURLConnection = (url.openConnection() as HttpURLConnection).apply {
                this.connectTimeout = 20 * 1000
                this.readTimeout = 20 * 1000
                this.requestMethod = requestMethod
                headers.forEach { (key, value) -> this.setRequestProperty(key, value) }
            }

            val status = connection.responseCode

            logD("<-- $requestMethod $status ${url.toURI()}")

            val reader: Reader = if (status > 299) {
                InputStreamReader(connection.errorStream)
            } else {
                InputStreamReader(connection.inputStream)
            }

            val content = reader.readText()

            logD("<-- $content")

            reader.close()

            return@runCatching Response(
                uri = url.toURI(),
                requestMethod = requestMethod,
                status = status,
                content = content
            )
        }

        return if (result.isSuccess) {
            val response = result.getOrThrow()
            when {
                response.status == 401 -> Result.failure(UnauthorizedException())
                response.status > 299 -> {
                    Result.failure(
                        HelloCustomerHttpException(
                            requestMethod = response.requestMethod,
                            uri = response.uri.toString(),
                            code = response.status,
                            content = response.content
                        )
                    )
                }
                else -> result
            }
        } else result.mapError()
    }

    private fun <T : Any> JsonAdapter<T>.requireFromJson(content: String): T {
        return runCatching {
            requireNotNull(this.fromJson(content)) {
                "Required a not-null DTO from JSON mapping."
            }
        }.onFailure { error ->
            apiErrorParser.parse(content)?.let { sdkException ->
                throw sdkException
            }
            throw error
        }.getOrThrow()
    }

    private fun logD(message: String) {
        if (message.length > 4000) {
            logger.d(message.substring(0, 4000))
            logD(message.substring(4000))
        } else {
            logger.d(message)
        }
    }

    private data class Response(
        val uri: URI,
        val requestMethod: String,
        val status: Int,
        val content: String
    )
}