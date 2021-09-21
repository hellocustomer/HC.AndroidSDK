package com.hellocustomer.sdk.network

internal interface UrlBuilder {

    fun buildUrl(): String

    fun buildHeaders(): Map<String, String> = emptyMap()
}