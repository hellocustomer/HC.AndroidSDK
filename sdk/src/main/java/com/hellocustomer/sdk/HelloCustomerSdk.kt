@file:JvmName("HelloCustomer")

package com.hellocustomer.sdk

import android.content.Context
import android.os.Handler
import com.hellocustomer.sdk.Instances.DefaultExecutorService
import com.hellocustomer.sdk.Instances.DefaultMainThreadHandler
import com.hellocustomer.sdk.Instances.SdkConfig
import com.hellocustomer.sdk.dialog.HelloCustomerDialog
import java.util.concurrent.ExecutorService

public object HelloCustomerSdk {

    public var loggingEnabled: Boolean = false

    /**
     *  Request loading touchpoint in any place and show when you want
     *  by calling [show][com.hellocustomer.sdk.dialog.HelloCustomerDialog] method in [onSuccess] callback.
     *
     *  @param context Context to get device configuration etc.
     *  @param config HelloCustomer configuration class
     *  @param executorService The service will perform background work
     *  @param mainThreadHandler Handler will invoke callbacks [onError] and [onSuccess]
     *  @param onError This callback will be called once the request throws an exception.
     *  @param onSuccess This callback will be called once the request finished successfully.
     *
     *  @see HelloCustomerDialog
     */
    @JvmOverloads
    public fun loadTouchpoint(
        context: Context,
        config: HelloCustomerTouchpointConfig,
        onSuccess: (HelloCustomerDialog) -> Unit,
        onError: (Throwable) -> Unit,
        executorService: ExecutorService = DefaultExecutorService,
        mainThreadHandler: Handler = DefaultMainThreadHandler,
    ): Unit = loadTouchpoint(
        context = context,
        touchpointConfig = config,
        sdkConfig = SdkConfig,
        onError = onError,
        onSuccess = onSuccess,
        executorService = executorService,
        mainThreadHandler = mainThreadHandler
    )

    /**
     *  Checks if touchpoint is set to production or not
     *
     *  @param context Context to get device configuration etc.
     *  @param config HelloCustomer configuration class
     *  @param executorService The service will perform background work
     *  @param mainThreadHandler Handler will invoke callbacks [onError] and [onSuccess]
     *  @param onError This callback will be called once the request throws an exception.
     *  @param onSuccess This callback will be called once the request finished successfully.
     *
     */
    public fun checkIfTouchpointIsActive(
        context: Context,
        config: HelloCustomerTouchpointConfig,
        executorService: ExecutorService = DefaultExecutorService,
        mainThreadHandler: Handler = DefaultMainThreadHandler,
        onSuccess: (Boolean) -> Unit,
        onError: (Throwable) -> Unit,
    ) {
        HelloCustomerService(
            executorService = executorService,
            mainThreadHandler = mainThreadHandler
        ).checkIfTouchpointIsActive(
            context = context,
            sdkConfig = Instances.SdkConfig,
            touchpointConfig = config,
            onError = onError,
            onSuccess = onSuccess
        )
    }

    private fun loadTouchpoint(
        context: Context,
        touchpointConfig: HelloCustomerTouchpointConfig,
        sdkConfig: SdkConfiguration,
        onError: (Throwable) -> Unit,
        onSuccess: (HelloCustomerDialog) -> Unit,
        executorService: ExecutorService,
        mainThreadHandler: Handler,
    ) {
        HelloCustomerService(
            executorService = executorService,
            mainThreadHandler = mainThreadHandler
        ).load(
            context = context,
            sdkConfig = sdkConfig,
            touchpointConfig = touchpointConfig,
            onError = onError,
            onSuccess = onSuccess
        )
    }
}
