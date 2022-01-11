package com.hellocustomer.sdk.network.dto

import com.squareup.moshi.Json

internal enum class QuestionTypeDto {
    @Json(name = "CES Score - 1-7 Question") CES,
    @Json(name = "NPS Score - 0-10 Question") NPS,
    @Json(name = "CSAT Score") CSAT,
    UNKNOWN
}