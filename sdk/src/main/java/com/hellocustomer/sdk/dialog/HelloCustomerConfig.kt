package com.hellocustomer.sdk.dialog

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class HelloCustomerConfig(
    val touchpointId: String,
    val title: String,
    val leftHint: String,
    val rightHint: String,
    @ColorInt val buttonBackgroundColor: Int?,
    @ColorInt val buttonTextColor: Int?,
    val buttonCount: Int,
    @ColorInt val paragraphColor: Int?,
    val paragraphFontName: String?,
    @ColorInt val textColor: Int?,
    val textFontName: String?,
    val surveyUrl: String
) : Parcelable