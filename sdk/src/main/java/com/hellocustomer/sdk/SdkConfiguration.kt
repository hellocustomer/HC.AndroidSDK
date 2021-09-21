package com.hellocustomer.sdk

internal interface SdkConfiguration {

    val baseApiUrl: String

    val baseApiScheme: String

    val baseOpinionsUrl: String

    val apiVersion: String
}