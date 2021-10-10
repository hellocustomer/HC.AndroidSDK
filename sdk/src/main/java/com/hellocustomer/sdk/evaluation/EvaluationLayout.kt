package com.hellocustomer.sdk.evaluation

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.google.android.flexbox.FlexboxLayout
import com.hellocustomer.sdk.R
import com.hellocustomer.sdk.dialog.HelloCustomerDialogConfig
import com.hellocustomer.sdk.utility.alpha
import com.hellocustomer.sdk.utility.getContrastColor

internal class EvaluationLayout : FlexboxLayout {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    private var evaluateClickListener: OnEvaluateClickListener? = null
    private val buttonClickListener = OnClickListener { button: View ->
        check(button is EvaluationButtonView)
        evaluateClickListener?.onClick(button.evaluation)
    }

    fun setOnEvaluateClickListener(listener: OnEvaluateClickListener) {
        this.evaluateClickListener = listener
    }

    fun generate(config: HelloCustomerDialogConfig) {

        for (number in 0..config.buttonCount) {
            val button = EvaluationButtonView(context)
            addView(button)

            button.updateLayoutParams<LayoutParams> {
                width = context.resources.getDimensionPixelSize(R.dimen.evaluation_button_size)
                height = context.resources.getDimensionPixelSize(R.dimen.evaluation_button_size)
            }

            button.apply {
                text = number.toString()
                insetTop = 0
                insetBottom = 0
                updatePadding(
                    left = context.resources.getDimensionPixelSize(R.dimen.size_8),
                    right = context.resources.getDimensionPixelSize(R.dimen.size_8),
                )
                config.buttonBackgroundColor?.let { color ->
                    backgroundTintList = ColorStateList.valueOf(color)
                    rippleColor = ColorStateList.valueOf(getContrastColor(backgroundColor = color) alpha 0.32)
                }
                config.buttonTextColor
                    ?.let(this::setTextColor)
            }
            button.setOnClickListener(buttonClickListener)
        }
    }

    fun interface OnEvaluateClickListener {
        fun onClick(score: Int)
    }
}