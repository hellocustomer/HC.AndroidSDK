package com.hellocustomer.sdk.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class QuestionDto(
    @Json(name = "language_UniqueID") val languageUniqueId: String,
    @Json(name = "language_Culture") val languageCulture: String,
    @Json(name = "text") val text: String,
    @Json(name = "siblingUniqueID") val siblingUniqueId: String,
    @Json(name = "hasbeenArchived") val hasBeenArchived: Boolean,
    @Json(name = "label1") val label1: String? = null,
    @Json(name = "label2") val label2: String? = null,
    @Json(name = "rateType") val rateType: Long? = null,
    @Json(name = "sortOrder") val sortOrder: Long,
    @Json(name = "type") val kind: QuestionKind,
    @Json(name = "hasAnswers") val hasAnswers: Boolean,
    @Json(name = "isMandatory") val isMandatory: Boolean,
    @Json(name = "campaign_UniqueID") val campaignUniqueId: String,
    @Json(name = "uniqueID") val uniqueID: String,
    @Json(name = "footerText") val footerText: String? = null
) {
    val isFirst: Boolean
        get() = sortOrder == 1L
}