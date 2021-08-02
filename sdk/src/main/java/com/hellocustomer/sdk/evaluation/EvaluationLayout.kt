package com.hellocustomer.sdk.evaluation

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.google.android.flexbox.FlexboxLayout
import com.hellocustomer.sdk.R
import com.hellocustomer.sdk.dialog.HelloCustomerConfig

internal class EvaluationLayout : FlexboxLayout {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null)

    var buttonClickListener: OnClickListener? = null
        set(value) {
            for (button in buttons) {
                button.setOnClickListener(value)
            }
        }

    private val buttons: Sequence<EvaluationButtonView>
        get() = children.filterIsInstance<EvaluationButtonView>()

    fun generate(config: HelloCustomerConfig) {

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
                config.buttonBackgroundColor
                    ?.let(this::setBackgroundColor)
                config.buttonTextColor
                    ?.let(this::setTextColor)
            }
        }

    }
}