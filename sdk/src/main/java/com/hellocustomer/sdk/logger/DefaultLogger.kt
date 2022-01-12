package com.hellocustomer.sdk.logger

import android.util.Log
import com.hellocustomer.sdk.HelloCustomerSdk

internal class DefaultLogger(private val tag: String? = null) : Logger {

    init {
        if (tag != null) {
            check(tag.length in 0..23) {
                "Maximum tag length is 23 characters. Was: ${tag.length}"
            }
        }
    }

    override fun d(message: String, throwable: Throwable?) = ifLoggerEnabled {
        Log.d(tag, message, throwable)
    }

    override fun d(message: String) = ifLoggerEnabled {
        d(message, null)
    }

    override fun w(message: String, throwable: Throwable?) = ifLoggerEnabled {
        Log.w(tag, message, throwable)
    }

    override fun w(message: String) = ifLoggerEnabled {
        w(message, null)
    }

    override fun v(message: String, throwable: Throwable?) = ifLoggerEnabled {
        Log.v(tag, message, throwable)
    }

    override fun v(message: String) = ifLoggerEnabled {
        v(message, null)
    }

    override fun e(throwable: Throwable?, message: String?) = ifLoggerEnabled {
        Log.e(tag, message, throwable)
    }

    override fun e(throwable: Throwable?) = ifLoggerEnabled {
        Log.e(tag, null, throwable)
    }

    override fun e(message: String) = ifLoggerEnabled {
        Log.e(tag, message)
    }

    private fun ifLoggerEnabled(action: () -> Unit){
        if (HelloCustomerSdk.loggingEnabled){
            action()
        }
    }
}