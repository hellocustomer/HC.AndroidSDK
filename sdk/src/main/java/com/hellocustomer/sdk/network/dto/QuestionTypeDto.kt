package com.hellocustomer.sdk.network.dto

import com.squareup.moshi.Json

internal enum class QuestionTypeDto {
    @Json(name = "NPS") NPS,
    @Json(name = "CES") CES,
    @Json(name = "CSAT") CSAT
}