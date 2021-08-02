package com.hellocustomer.sdk.evaluation

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.ColorInt
import com.google.android.material.button.MaterialButton
import com.hellocustomer.sdk.R

internal class EvaluationButtonView : MaterialButton {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, com.google.android.material.R.attr.materialButtonStyle)

    constructor(context: Context) : this(context, null)

    /**
     * Parses the text as an Int number and returns the result
     * or -1 if the text is not a valid representation of a number.
     */
    val evaluation: Int
        get() = text.toString().toIntOrNull() ?: -1

    init {
        setSupportAllCaps(true)
    }
}