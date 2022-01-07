package com.hellocustomer.sdk

import com.hellocustomer.sdk.font.FontBuilder
import java.util.UUID

public data class HelloCustomerTouchpointConfig(
    val authorizationToken: String,
    val companyId: UUID,
    val touchpointId: UUID,
    val metadata: HelloCustomerMetadata = emptyMap(),
    val respondentFirstName: String? = null,
    val respondentLastName: String? = null,
    val respondentEmailAddress: String? = null,
    val questionFont: FontBuilder? = null,
    val hintFont: FontBuilder? = null
)