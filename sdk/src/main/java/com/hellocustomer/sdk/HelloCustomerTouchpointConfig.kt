package com.hellocustomer.sdk

import com.hellocustomer.sdk.font.FontBuilder

public data class HelloCustomerTouchpointConfig(
    val authorizationToken: String,
    val companyId: String,
    val touchpointId: String,
    val metadata: HelloCustomerMetadata = emptyMap(),
    val respondentFirstName: String? = null,
    val respondentLastName: String? = null,
    val respondentEmailAddress: String? = null,
    val questionFont: FontBuilder? = null,
    val hintFont: FontBuilder? = null
)