package com.hellocustomer.sdk.network

import com.hellocustomer.sdk.network.dto.TouchpointDto

internal interface HelloCustomerApi {

    suspend fun getTouchpoint(token: String): TouchpointDto
}