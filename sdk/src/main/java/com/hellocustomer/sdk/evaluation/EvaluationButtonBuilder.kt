package com.hellocustomer.sdk.evaluation

import android.content.Context
import android.content.res.ColorStateList
import android.os.Parcelable
import androidx.annotation.ColorInt
import com.hellocustomer.sdk.R
import com.hellocustomer.sdk.network.dto.QuestionTypeDto
import com.hellocustomer.sdk.utility.getCompatColor
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class EvaluationButtonBuilder(
    private val questionTypeDto: QuestionTypeDto,
    private val useColorScale: Boolean,
    @ColorInt private val buttonBackgroundColor: Int?,
    @ColorInt private val buttonTextColor: Int?
) : Parcelable {

    init {
        check(questionTypeDto != QuestionTypeDto.UNKNOWN) {
            "Question type is unknown."
        }
    }

    fun build(context: Context): Array<EvaluationButtonView> = when (questionTypeDto) {
        QuestionTypeDto.NPS -> Array(10) { scoreValue ->
            createButton(
                scoreValue = scoreValue,
                context = context,
                scaleButtonColor = when (scoreValue) {
                    in 0..1 -> context.getCompatColor(R.color.guardsmanRed)
                    in 2..3 -> context.getCompatColor(R.color.trinidad)
                    in 4..6 -> context.getCompatColor(R.color.california)
                    in 7..8 -> context.getCompatColor(R.color.keyLimePie)
                    in 9..10 -> context.getCompatColor(R.color.chartreuse)
                    else -> throw IllegalStateException("Score value out of range: $scoreValue")
                }
            )
        }
        QuestionTypeDto.CES -> Array(7) { index ->
            val scoreValue = index.plus(1)
            createButton(
                scoreValue = scoreValue,
                context = context,
                scaleButtonColor = when (scoreValue) {
                    in 1..2 -> context.getCompatColor(R.color.guardsmanRed)
                    3 -> context.getCompatColor(R.color.trinidad)
                    in 4..5 -> context.getCompatColor(R.color.california)
                    in 6..7 -> context.getCompatColor(R.color.keyLimePie)
                    else -> throw IllegalStateException("Score value out of range: $scoreValue")
                }
            )
        }
        QuestionTypeDto.CSAT -> Array(5) { index ->
            val scoreValue = index.plus(1)
            createButton(
                scoreValue = scoreValue,
                context = context,
                scaleButtonColor = when (scoreValue) {
                    1 -> context.getCompatColor(R.color.guardsmanRed)
                    2 -> context.getCompatColor(R.color.trinidad)
                    3 -> context.getCompatColor(R.color.california)
                    4 -> context.getCompatColor(R.color.keyLimePie)
                    5 -> context.getCompatColor(R.color.chartreuse)
                    else -> throw IllegalStateException("Score value out of range: $scoreValue")
                }
            )
        }
        else -> emptyArray()
    }

    private fun createButton(
        scoreValue: Int,
        context: Context,
        @ColorInt scaleButtonColor: Int
    ): EvaluationButtonView {
        return EvaluationButtonView(context).apply {
            text = scoreValue.toString()
            @ColorInt val backgroundColor: Int? = if (useColorScale) scaleButtonColor else buttonBackgroundColor
            backgroundColor?.let { this.backgroundTintList = ColorStateList.valueOf(it) }
            buttonTextColor?.let(this::setTextColor)
        }
    }
}