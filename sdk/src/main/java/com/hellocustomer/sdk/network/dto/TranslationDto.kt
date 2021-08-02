package com.hellocustomer.sdk.network.dto

internal data class TranslationDto (
    /**
     * Language abbreviation.
     */
    val language: String,
    val question: String,
    val leftHint: String,
    val rightHint: String,
    /**
     * Use as default language.
     */
    val isDefault: Boolean
)