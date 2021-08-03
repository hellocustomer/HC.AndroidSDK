package com.hellocustomer.sdk.network

import com.google.gson.Gson
import com.hellocustomer.sdk.exception.HelloCustomerSdkException
import com.hellocustomer.sdk.network.dto.TouchpointDto
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL

internal class HelloCustomerApiImpl(
    private val gson: Gson
) : HelloCustomerApi {

    override suspend fun getTouchpoint(token: String): Result<TouchpointDto> =
        call(URL("http://192.168.10.140:8080/workshop/hc"), "GET")
            .map { content ->
                gson.fromJson(content, TouchpointDto::class.java)
            }
            .mapError()

    private fun <T> Result<T>.mapError(): Result<T> {
        return when {
            isSuccess -> this
            isFailure -> Result.failure(
                exception = HelloCustomerSdkException(
                    throwable = requireNotNull(this.exceptionOrNull())
                )
            )
            else -> throw IllegalStateException("Illegal state of the internal API. Result couldn't be success and failure simultaneously or neither of them.")
        }
    }

    private fun call(url: URL, requestMethod: String): Result<String> = kotlin.runCatching {
        val connection: HttpURLConnection = (url.openConnection() as HttpURLConnection).apply {
            this.connectTimeout = 5000
            this.readTimeout = 5000
            this.requestMethod = requestMethod
        }

        val status = connection.responseCode

        val reader: Reader = if (status > 299) {
            InputStreamReader(connection.errorStream)
        } else {
            InputStreamReader(connection.inputStream)
        }

        val content = reader.readText()

        reader.close()

        return@runCatching content
    }
}