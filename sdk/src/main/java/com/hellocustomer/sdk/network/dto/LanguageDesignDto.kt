package com.hellocustomer.sdk.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class LanguageDesignDto(
    @Json(name ="language_UniqueID") val languageUniqueId: String,
    @Json(name ="opinions_Button_TextColor") val opinionsButtonTextColor: String,
    @Json(name ="opinions_Button_BgColor") val opinionsButtonBgColor: String,
    @Json(name ="opinions_UseColorScale") val opinionsUseColorScale: Boolean,
    @Json(name ="surveyType") val dialogType: DialogTypeDto,
    @Json(name ="opinions_Questions_Color") val opinionsQuestionsColor: String?,
    @Json(name ="opinions_Paragraph_Color") val opinionsParagraphColor: String?
)