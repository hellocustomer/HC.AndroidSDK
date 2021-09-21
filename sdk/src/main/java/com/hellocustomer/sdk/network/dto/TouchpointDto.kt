package com.hellocustomer.sdk.network.dto

import com.squareup.moshi.Json
import java.util.Locale

internal data class TouchpointDto(
    @Json(name = "campaign_UniqueID") val campaignUniqueID: String,
    @Json(name = "hasbeenArchived") val hasBeenArchived: Boolean,
    @Json(name = "sortOrder") val sortOrder: Long,
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