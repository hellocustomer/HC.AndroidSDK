package com.hellocustomer.sdk.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class LanguageDesignDto(
    @Json(name ="language_UniqueID") val languageUniqueId: String,
    @Json(name ="content_LabelCopyright") val contentLabelCopyright: String,
    @Json(name ="response_Submitted") val responseSubmitted: String,
    @Json(name ="response_Quarantine") val responseQuarantine: String,
    @Json(name ="language_Name") val languageName: String,
    @Json(name ="opinions_Button_TextColor") val opinionsButtonTextColor: String,
    @Json(name ="opinions_Button_BgColor") val opinionsButtonBgColor: String,
    @Json(name ="opinions_Button_Text") val opinionsButtonText: String,
    @Json(name ="campaign_UniqueID") val campaignUniqueID: String,
    @Json(name ="opinions_UseColorScale") val opinionsUseColorScale: Boolean,
    @Json(name ="opinions_UseVerticalScaleForMobile") val opinionsUseVerticalScaleForMobile: Boolean,
    @Json(name ="surveyType") val dialogType: DialogTypeDto,
    @Json(name ="opinions_Questions_Font") val opinionsQuestionsFont: String,
    @Json(name ="opinions_Questions_Color") val opinionsQuestionsColor: String,
    @Json(name ="opinions_Paragraph_Font") val opinionsParagraphFont: String,
    @Json(name ="opinions_Paragraph_Color") val opinionsParagraphColor: String,
    @Json(name ="opinions_ShowLogo") val opinionsShowLogo: Boolean,
    @Json(name ="uniqueID") val uniqueId: String
)