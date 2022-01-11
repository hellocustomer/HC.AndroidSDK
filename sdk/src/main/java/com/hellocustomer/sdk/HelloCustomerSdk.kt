@file:JvmName("HelloCustomer")

package com.hellocustomer.sdk

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat
import com.hellocustomer.sdk.dialog.HelloCustomerDialog
import com.hellocustomer.sdk.logger.DefaultLogger
import com.hellocustomer.sdk.network.HelloCustomerApi
import com.hellocustomer.sdk.network.HelloCustomerApiImpl
import com.hellocustomer.sdk.network.adapter.DialogTypeDtoJsonAdapter
import com.hellocustomer.sdk.network.dto.LanguageDesignDto
import com.hellocustomer.sdk.network.dto.QuestionTypeDto
import com.hellocustomer.sdk.network.dto.TouchpointDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.EnumJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

//region Public API

public object HelloCustomerSdk {
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
            sdkConfig = SdkConfig,
            touchpointConfig = config,
            onError = onError,
            onSuccess = onSuccess
        )
    }
}



//endregion

//region Internal API

internal val DefaultExecutorService: ExecutorService by lazy {
    Executors.newSingleThreadExecutor()
}
internal val DefaultMainThreadHandler: Handler by lazy {
    HandlerCompat.createAsync(Looper.getMainLooper())
}
internal val SdkConfig: SdkConfiguration = HelloCustomerSdkConfig(
    baseApiUrl = "api.hellocustomer.com",
    baseOpinionsUrl = "opinions.hellocustomer.com",
    baseApiScheme = "https",
    apiVersion = "V2.0"
)
internal val MoshiInstance: Moshi = Moshi.Builder()
    .add(DialogTypeDtoJsonAdapter())
    .add(
        QuestionTypeDto::class.java,
        EnumJsonAdapter.create(QuestionTypeDto::class.java)
            .withUnknownFallback(QuestionTypeDto.UNKNOWN)
    )
    .addLast(KotlinJsonAdapterFactory())
    .build()
internal val SdkLogger = DefaultLogger(tag = "HELLO_CUSTOMER")
internal val SdkApi: HelloCustomerApi = HelloCustomerApiImpl(
    touchpointAdapter = Types.newParameterizedType(List::class.java, TouchpointDto::class.java).let { type ->
        MoshiInstance.adapter(type)
    },
    designAdapter = Types.newParameterizedType(List::class.java, LanguageDesignDto::class.java).let { type ->
        MoshiInstance.adapter(type)
    },
    logger = DefaultLogger(tag = "HELLO_CUSTOMER_HTTP")
)

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

//endregion