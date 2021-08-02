package com.hellocustomer.sdk

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

@ColorInt
internal fun Context.themeColor(@AttrRes attrRes: Int, @ColorInt defValue: Int = Color.WHITE): Int =
    TypedValue().let { typedValue ->
        val values: TypedArray = this.obtainStyledAttributes(typedValue.data, intArrayOf(attrRes))
        val color = values.getColor(0, defValue)

        values.recycle()

        return color
    }

internal fun Context.isPermissionGranted(permission: String): Boolean =
    PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, permission)

internal fun Context.toastShort(message: String) = Toast.makeText(
    this,
    message, Toast.LENGTH_SHORT
).show()

internal fun Context.getCompatColorStateList(@ColorRes colorRes: Int) =
    ColorStateList.valueOf(ContextCompat.getColor(this, colorRes))

internal fun Context.getCompatColor(@ColorRes colorRes: Int) =
    ContextCompat.getColor(this, colorRes)

internal fun Context.toast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
