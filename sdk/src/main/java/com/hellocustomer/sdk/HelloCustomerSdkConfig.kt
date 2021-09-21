package com.hellocustomer.sdk

internal data class HelloCustomerSdkConfig(
    override val baseApiUrl: String,
    override val baseApiScheme: String,
    override val baseOpinionsUrl: String,
    override val apiVersion: String
) : SdkConfiguration