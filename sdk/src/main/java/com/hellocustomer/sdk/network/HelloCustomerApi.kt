package com.hellocustomer.sdk.network

import com.hellocustomer.sdk.network.dto.LanguageDesignDto
import com.hellocustomer.sdk.network.dto.TouchpointDto

internal interface HelloCustomerApi {

    fun getQuestions(builder: UrlBuilder): Result<Collection<TouchpointDto>>

    fun getLanguageDesigns(builder: UrlBuilder): Result<Collection<LanguageDesignDto>>
}