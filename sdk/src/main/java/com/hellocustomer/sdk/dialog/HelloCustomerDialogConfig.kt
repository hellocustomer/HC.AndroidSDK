package com.hellocustomer.sdk.dialog

import android.os.Parcelable
import androidx.annotation.ColorInt
import com.hellocustomer.sdk.evaluation.EvaluationButtonBuilder
import com.hellocustomer.sdk.font.FontBuilder
import com.hellocustomer.sdk.survey.SurveyUriBuilder
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
internal data class HelloCustomerDialogConfig(
    val touchpointId: UUID,
    val questionText: String,
    val leftHint: String,
    val rightHint: String,
    val questionLabels: Map<Int, String>,
    val labeledQuestionView: Boolean,
    val buttonBuilder: EvaluationButtonBuilder,
    @ColorInt val questionTextColor: Int?,
    val questionFont: FontBuilder?,
    @ColorInt val hintTextColor: Int?,
    val hintFont: FontBuilder?,
    val surveyUriBuilder: SurveyUriBuilder,
    val dialogType: DialogType
) : Parcelable