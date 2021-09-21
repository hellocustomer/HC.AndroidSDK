package com.hellocustomer.sdk.dialog

import android.os.Parcelable
import androidx.annotation.ColorInt
import com.hellocustomer.sdk.font.FontBuilder
import com.hellocustomer.sdk.survey.SurveyUriBuilder
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class HelloCustomerDialogConfig(
    val touchpointId: String,
    val questionText: String,
    val leftHint: String,
    val rightHint: String,
    @ColorInt val buttonBackgroundColor: Int?,
    @ColorInt val buttonTextColor: Int?,
    val buttonCount: Int,
    @ColorInt val questionTextColor: Int?,
    val questionFont: FontBuilder?,
    @ColorInt val hintTextColor: Int?,
    val hintFont: FontBuilder?,
    val surveyUriBuilder: SurveyUriBuilder,
    val dialogType: DialogType
) : Parcelable