package com.hellocustomer.sdk.network.dto

import com.squareup.moshi.Json

internal enum class QuestionTypeDto {
    @Json(name = "CES Score - 0-7 Question") NPS,
    @Json(name = "NPS Score - 0-10 Question") CES,
    @Json(name = "CSAT Score") CSAT,
    UNKNOWN
}