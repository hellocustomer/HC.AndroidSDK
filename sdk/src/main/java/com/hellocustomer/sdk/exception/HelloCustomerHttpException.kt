package com.hellocustomer.sdk.exception

public data class HelloCustomerHttpException internal constructor(
    public val requestMethod: String,
    public val uri: String,
    public val code: Int,
    public val content: String
) : HelloCustomerSdkException()