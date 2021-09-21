package com.hellocustomer.sdk.locale

import android.content.Context
import android.os.Build
import java.util.Locale

internal class UserLocaleProviderImpl(
    private val context: Context,
) : UserLocaleProvider {

    override fun get(): Locale {
        val preferred: Locale? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            context.resources.configuration.locales[0]
        } else{
            context.resources.configuration.locale
        }

        return preferred ?: Locale.ENGLISH
    }
}