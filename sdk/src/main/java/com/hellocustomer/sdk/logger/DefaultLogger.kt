package com.hellocustomer.sdk.logger

import android.util.Log

internal val SdkLogger = DefaultLogger(tag = "GLOBAL")

internal fun Any.defaultLogger(): Logger = DefaultLogger(tag = this.javaClass.simpleName)

internal class DefaultLogger(private val tag: String? = null) : Logger {

    override fun d(message: String, throwable: Throwable?) {
        Log.d(tag, message, throwable)
    }

    override fun d(message: String) =
        d(message, null)

    override fun w(message: String, throwable: Throwable?) {
        Log.w(tag, message, throwable)
    }

    override fun w(message: String) =
        w(message, null)

    override fun v(message: String, throwable: Throwable?) {
        Log.v(tag, message, throwable)
    }

    override fun v(message: String) =
        v(message, null)

    override fun e(throwable: Throwable?, message: String) {
        Log.e(tag, message, throwable)
    }

    override fun e(message: String) =
        e(null, message)
}