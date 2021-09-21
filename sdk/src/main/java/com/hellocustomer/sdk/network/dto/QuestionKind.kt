package com.hellocustomer.sdk.network.dto

import com.squareup.moshi.Json

internal data class QuestionKind(
    @Json(name = "name") val type: QuestionTypeDto,
    @Json(name = "hasScore") val hasScore: Boolean,
    @Json(name = "hasText") val hasText: Boolean,
    @Json(name = "hasBoolean") val hasBoolean: Boolean,
    @Json(name = "siblingUniqueID") val siblingUniqueID: String,
    @Json(name = "defaultRateType") val defaultRateType: Long? = null,
    @Json(name = "hasDefaultText") val hasDefaultText: Boolean,
    @Json(name = "hasMetadata") val hasMetadata: Boolean,
    @Json(name = "isLabel1Required") val isLabel1Required: Boolean,
    @Json(name = "isLabel2Required") val isLabel2Required: Boolean,
    @Json(name = "isLabel3Required") val isLabel3Required: Boolean,
    @Json(name = "isLabel4Required") val isLabel4Required: Boolean,
    @Json(name = "isLabel5Required") val isLabel5Required: Boolean,
    @Json(name = "isLabel6Required") val isLabel6Required: Boolean,
    @Json(name = "isLabel7Required") val isLabel7Required: Boolean,
    @Json(name = "uniqueID") val uniqueID: String
)