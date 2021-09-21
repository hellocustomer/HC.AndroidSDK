package com.hellocustomer.sdk.locale

import java.util.Locale

internal interface UserLocaleProvider {
    fun get(): Locale
}