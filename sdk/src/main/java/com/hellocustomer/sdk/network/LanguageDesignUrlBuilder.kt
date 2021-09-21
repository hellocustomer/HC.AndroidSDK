package com.hellocustomer.sdk.network

import android.net.Uri

internal class LanguageDesignUrlBuilder(
    private val basePath: String,
    private val authorizationToken: String
) : UrlBuilder {

    override fun buildUrl(): String = Uri.Builder()
        .encodedPath(basePath)
        .appendPath("askanywheretemplate")
        .appendPath("GetTouchpointSurveyDesign")
        .build()
        .toString()

    override fun buildHeaders(): Map<String, String> = mapOf(
        AUTHORIZATION_HEADER to "$AUTHORIZATION_BASIC $authorizationToken"
    )
}