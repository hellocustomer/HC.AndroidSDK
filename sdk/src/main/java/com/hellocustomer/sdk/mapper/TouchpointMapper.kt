package com.hellocustomer.sdk.mapper

import android.graphics.Color
import com.hellocustomer.sdk.dialog.HelloCustomerConfig
import com.hellocustomer.sdk.network.dto.QuestionTypeDto
import com.hellocustomer.sdk.network.dto.TouchpointDto
import com.hellocustomer.sdk.network.dto.TranslationDto

internal object TouchpointMapper {

    fun toConfig(dto: TouchpointDto, language: TranslationDto): HelloCustomerConfig {
        return HelloCustomerConfig(
            title = language.question,
            buttonBackgroundColor = dto.buttonBackgroundColor.let(Color::parseColor),
            buttonTextColor = dto.buttonTextColor.let(Color::parseColor),
            buttonCount = when (dto.questionType) {
                QuestionTypeDto.NPS -> 10
                QuestionTypeDto.CES -> 7
                QuestionTypeDto.CSAT -> 5
            },
            rightHint = language.rightHint,
            leftHint = language.leftHint,
            paragraphColor = dto.paragraphColor.let(Color::parseColor),
            textColor = dto.textColor.let(Color::parseColor),
            surveyUrl = dto.surveyURL
        )
    }
}