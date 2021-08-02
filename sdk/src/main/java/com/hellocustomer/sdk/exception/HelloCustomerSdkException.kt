package com.hellocustomer.sdk.exception

public open class HelloCustomerSdkException : RuntimeException {

    internal constructor() : super()

    internal constructor(message: String) : super(message)

    internal constructor(message: String, throwable: Throwable) : super(message, throwable)
}