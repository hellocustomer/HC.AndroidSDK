package com.hellocustomer.sdk.network

import com.hellocustomer.sdk.MoshiInstance
import com.hellocustomer.sdk.exception.ApiErrorMessageException
import com.hellocustomer.sdk.exception.CampaignIsOutOfProductionException
import com.hellocustomer.sdk.exception.HelloCustomerSdkException
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types

internal const val CampaignIsOutOfProductionApiError = "The campaign is not set to production."

internal class ApiErrorParser {

    private val apiMessageJsonAdapter: JsonAdapter<List<String>> = MoshiInstance.adapter(Types.newParameterizedType(List::class.java, String::class.java))

    fun parse(json: String): HelloCustomerSdkException? {
        return runCatching {
            apiMessageJsonAdapter.fromJson(json)?.first()
        }.map { apiErrorMessage ->
            when(apiErrorMessage){
                CampaignIsOutOfProductionApiError -> CampaignIsOutOfProductionException()
                null -> null
                else -> ApiErrorMessageException(apiErrorMessage)
            }
        }.getOrNull()
    }
}