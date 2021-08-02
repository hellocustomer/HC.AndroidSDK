package com.hellocustomer.sdk.logger

internal interface Logger {

    fun d(message: String, throwable: Throwable?)

    fun d(message: String)

    fun w(message: String, throwable: Throwable?)

    fun w(message: String)

    fun v(message: String, throwable: Throwable?)

    fun v(message: String)

    fun e(throwable: Throwable?, message: String)

    fun e(message: String)
}