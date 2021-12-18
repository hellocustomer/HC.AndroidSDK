package com.hellocustomer.sdk.mapper

import android.graphics.Color
import androidx.annotation.ColorInt
import com.hellocustomer.sdk.HelloCustomerTouchpointConfig
import com.hellocustomer.sdk.dialog.DialogType
import com.hellocustomer.sdk.dialog.HelloCustomerDialogConfig
import com.hellocustomer.sdk.evaluation.EvaluationButtonBuilder
import com.hellocustomer.sdk.network.dto.DialogTypeDto
import com.hellocustomer.sdk.network.dto.LanguageDesignDto
import com.hellocustomer.sdk.network.dto.QuestionDto
import com.hellocustomer.sdk.network.dto.QuestionTypeDto
import com.hellocustomer.sdk.survey.SurveyUriBuilder

internal object TouchpointMapper {

    fun toConfig(
        config: HelloCustomerTouchpointConfig,
        firstQuestion: QuestionDto,
        languageDesignDto: LanguageDesignDto,
        fallbackDialogType: DialogType,
        surveyUriBuilder: SurveyUriBuilder
    ): HelloCustomerDialogConfig {
        val labeledQuestionView = firstQuestion.rateType == 4L && firstQuestion.kind.type == QuestionTypeDto.CES
        return HelloCustomerDialogConfig(
            questionText = firstQuestion.text,
            buttonBuilder = EvaluationButtonBuilder(
                questionTypeDto = firstQuestion.kind.type,
                useColorScale = languageDesignDto.opinionsUseColorScale,
                labels = firstQuestion.labels,
                useCustomLabels = labeledQuestionView,
                buttonBackgroundColor = languageDesignDto.opinionsButtonBgColor.let(this::parseColor),
                buttonTextColor = languageDesignDto.opinionsButtonTextColor.let(this::parseColor),
            ),
            questionLabels = firstQuestion.labels,
            labeledQuestionView = labeledQuestionView,
            rightHint = firstQuestion.label1 ?: "",
            leftHint = firstQuestion.label2 ?: "",
            questionTextColor = languageDesignDto.opinionsQuestionsColor.let(this::parseColor),
            hintTextColor = languageDesignDto.opinionsParagraphColor.let(this::parseColor),
            surveyUriBuilder = surveyUriBuilder,
            questionFont = config.questionFont,
            hintFont = config.hintFont,
            touchpointId = config.touchpointId,
            dialogType = when (languageDesignDto.dialogType) {
                DialogTypeDto.BOTTOM -> DialogType.BOTTOM
                DialogTypeDto.CENTER -> DialogType.CENTER
                DialogTypeDto.UNKNOWN -> fallbackDialogType
            }
        )
    }

    @ColorInt
    private fun parseColor(value: String): Int = Color.parseColor(value.normalizeHexColor())

    private fun String.normalizeHexColor(): String {
        return if (this.length == 4) {
            this.padEnd(7, this.last())
        } else this
    }
}