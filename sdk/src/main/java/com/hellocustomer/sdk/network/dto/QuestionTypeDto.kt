package com.hellocustomer.sdk.network.dto

import com.google.gson.annotations.SerializedName

internal enum class QuestionTypeDto {
    @SerializedName("NPS") NPS,
    @SerializedName("CES") CES,
    @SerializedName("CSAT") CSAT
}