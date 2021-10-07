package com.hellocustomer.sdk.utility

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.core.graphics.ColorUtils

private const val LUMA_RED = 0.299
private const val LUMA_GREEN = 0.587
private const val LUMA_BLUE = 0.114
private const val RGB_MAX_RANGE_VALUE = 255

@ColorInt
internal infix fun @receiver:ColorInt Int.alpha(@FloatRange(from = 0.0, to = 1.0) alpha: Double) =
    ColorUtils.setAlphaComponent(this, (alpha * RGB_MAX_RANGE_VALUE).toInt())

@ColorInt
internal fun getContrastColor(
    backgroundColor: Int,
    @ColorInt darkColor: Int = Color.BLACK,
    @ColorInt lightColor: Int = Color.WHITE
): Int {
    // Rec. 601 luma formula
    val darkness = 1 - (
        LUMA_RED * Color.red(backgroundColor)
            + LUMA_GREEN * Color.green(backgroundColor)
            + LUMA_BLUE * Color.blue(backgroundColor)
        ) / RGB_MAX_RANGE_VALUE
    return if (darkness < 0.5) {
        darkColor
    } else {
        lightColor
    }
}