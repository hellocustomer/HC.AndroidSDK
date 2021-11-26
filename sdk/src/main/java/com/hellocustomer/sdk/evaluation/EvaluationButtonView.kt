package com.hellocustomer.sdk.evaluation

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

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
    var evaluation: Int = -1

    init {
        setSupportAllCaps(true)
    }
}