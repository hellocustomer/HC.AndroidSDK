package com.hellocustomer.sdk.network

import com.google.gson.GsonBuilder
import com.hellocustomer.sdk.network.dto.TouchpointDto
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL

internal class HelloCustomerApiImpl : HelloCustomerApi {

    override suspend fun getTouchpoint(token: String): TouchpointDto {
        return call(URL("http://192.168.10.140:8080/workshop/hc"), "GET").map { content ->
            GSON.fromJson(content, TouchpointDto::class.java)
        }.getOrThrow()
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

    companion object {

        private val GSON = GsonBuilder().create()
    }
}