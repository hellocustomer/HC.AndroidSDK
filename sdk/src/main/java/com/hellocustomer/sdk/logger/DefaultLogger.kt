package com.hellocustomer.sdk.logger

import android.util.Log

internal class DefaultLogger(private val tag: String? = null) : Logger {

    init {
        if (tag != null) {
            check(tag.length in 0..23) {
                "Maximum tag length is 23 characters. Was: ${tag.length}"
            }
        }
    }

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

    override fun e(throwable: Throwable?, message: String?) {
        Log.e(tag, message, throwable)
    }

    override fun e(throwable: Throwable?) {
        Log.e(tag, null, throwable)
    }

    override fun e(message: String) {
        Log.e(tag, message)
    }
}