package com.hellocustomer.sdk.evaluation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import com.hellocustomer.sdk.R
import com.hellocustomer.sdk.databinding.EvaluationCardViewBinding
import com.hellocustomer.sdk.dialog.HelloCustomerDialogConfig
import com.hellocustomer.sdk.utility.getCompatColor

internal class EvaluationCardView : CardView {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        cardElevation = 0f
        radius = context.resources.getDimensionPixelSize(R.dimen.size_8).toFloat()
        setCardBackgroundColor(context.getCompatColor(R.color.white))
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private val binding = EvaluationCardViewBinding.inflate(LayoutInflater.from(context), this)

    val closeButton: MaterialButton
        get() = binding.closeButton

    fun setOnEvaluateClickListener(listener: EvaluationLayout.OnEvaluateClickListener) {
        binding.evaluationLayout.setOnEvaluateClickListener(listener)
    }

    fun bind(config: HelloCustomerDialogConfig): Unit = with(binding) {
        evaluationLayout.generate(config)
        headingTextView.text = config.questionText
        leftHint.text = config.leftHint
        rightHint.text = config.rightHint

        config.questionTextColor
            ?.let(headingTextView::setTextColor)
        config.hintTextColor
            ?.let { color ->
                leftHint.setTextColor(color)
                rightHint.setTextColor(color)
            }
        config.hintFont?.create(context)?.let { typeface ->
            leftHint.typeface = typeface
            rightHint.typeface = typeface
        }
        config.questionFont?.create(context)
            ?.let(headingTextView::setTypeface)

        if (config.labeledQuestionView){
            leftHint.visibility = GONE
            rightHint.visibility = GONE
        }
    }
}