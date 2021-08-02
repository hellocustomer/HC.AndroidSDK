package com.hellocustomer.sdk.dialog

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class HelloCustomerConfig(
    val title: String = "",
    val leftHint: String = "",
    val rightHint: String = "",
    @ColorInt val buttonBackgroundColor: Int? = null,
    @ColorInt val buttonTextColor: Int? = null,
    val buttonCount: Int = 0,
    @ColorInt val paragraphColor: Int? = null,
    @ColorInt val textColor: Int? = null,
    val surveyUrl: String = ""
) : Parcelable