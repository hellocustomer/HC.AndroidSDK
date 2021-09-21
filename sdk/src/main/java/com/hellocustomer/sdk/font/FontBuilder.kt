package com.hellocustomer.sdk.font

import android.content.Context
import android.graphics.Typeface
import android.os.Parcelable
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import kotlinx.parcelize.Parcelize
import java.io.File

public sealed class FontBuilder : Parcelable {

    internal abstract fun create(context: Context): Typeface

    @Parcelize
    public data class FromPath(private val path: String) : FontBuilder() {

        override fun create(context: Context): Typeface =
            Typeface.createFromFile(path)
    }

    @Parcelize
    public data class FromFile(private val file: File) : FontBuilder() {

        override fun create(context: Context): Typeface =
            Typeface.createFromFile(file)
    }

    @Parcelize
    public data class FromId(@FontRes private val fontId: Int) : FontBuilder() {

        override fun create(context: Context): Typeface =
            requireNotNull(ResourcesCompat.getFont(context, fontId)) {
                "Cannot get font from resources."
            }
    }
}