package com.hellocustomer.sdk.evaluation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.google.android.material.button.MaterialButton
import com.hellocustomer.sdk.R
import com.hellocustomer.sdk.databinding.EvaluationCardViewBinding
import com.hellocustomer.sdk.dialog.HelloCustomerConfig
import com.hellocustomer.sdk.getCompatColor

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

    var buttonClickListener: OnClickListener?
        get() = binding.evaluationLayout.buttonClickListener
        set(value) {
            binding.evaluationLayout.buttonClickListener = value
        }

    fun bind(config: HelloCustomerConfig): Unit = with(binding) {
        evaluationLayout.generate(config)
        headingTextView.text = config.title
        leftHint.text = config.leftHint
        rightHint.text = config.rightHint

        config.paragraphColor
            ?.let(headingTextView::setTextColor)
        config.textColor
            ?.let { color ->
                leftHint.setTextColor(color)
                rightHint.setTextColor(color)
            }
    }

}