package com.hellocustomer.sdk.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Locale

@JsonClass(generateAdapter = true)
internal data class TouchpointDto(
    @Json(name = "campaign_UniqueID") val campaignUniqueID: String,
    @Json(name = "hasbeenArchived") val hasBeenArchived: Boolean,
    @Json(name = "sortOrder") val sortOrder: Long,
    @Json(name = "campaign_CampaignType_ID") val campaignType: Int?,
    @Json(name = "items") val questions: List<QuestionDto>,
    @Json(name = "uniqueID") val uniqueID: String
) {

    fun findByDefaultLanguage(currentLocale: Locale): QuestionDto? =
        this.questions.find {
            it.isFirst && it.languageCulture.equals(currentLocale.language, ignoreCase = true)
        } ?: this.questions.firstOrNull {
            it.isFirst && "EN".equals(currentLocale.language, ignoreCase = true)
        } ?: this.questions.firstOrNull(QuestionDto::isFirst)
}