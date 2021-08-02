package com.hellocustomer.sdk.network.dto

import java.util.Locale

internal data class TouchpointDto (
    val translations: List<TranslationDto>,
    val questionType: QuestionTypeDto,
    val touchpointId: String,
    val buttonTextColor: String,
    val buttonBackgroundColor: String,
    val textFontName: String,
    val textColor: String,
    val paragraphFontName: String,
    val paragraphColor: String,
    val dialogType: DialogTypeDto,
    val surveyURL: String
) {

    fun findDefaultLanguage(currentLocale: Locale): TranslationDto? =
        this.translations
            .find { it.language.equals(currentLocale.language, ignoreCase = true) }
            ?: this.translations.firstOrNull { it.isDefault }
}