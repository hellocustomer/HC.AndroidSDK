@file:JvmName("HelloCustomer")

package com.hellocustomer.sdk

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat
import androidx.fragment.app.Fragment
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

@JvmOverloads
public fun AppCompatActivity.loadTouchpoint(
    config: HelloCustomerTouchpointConfig,
    executorService: ExecutorService = DefaultExecutorService,
    mainThreadHandler: Handler = DefaultMainThreadHandler,
    onError: (Throwable) -> Unit = handleError::invoke,
    onSuccess: (HelloCustomerDialog) -> Unit = handleSuccess::invoke
): Unit = loadTouchpoint(
    context = this,
    config = config,
    onSuccess = onSuccess,
    onError = onError,
    executorService = executorService,
    mainThreadHandler = mainThreadHandler
)

@JvmOverloads
public fun Fragment.loadTouchpoint(
    config: HelloCustomerTouchpointConfig,
    executorService: ExecutorService = DefaultExecutorService,
    mainThreadHandler: Handler = DefaultMainThreadHandler,
    onError: (Throwable) -> Unit = handleError::invoke,
    onSuccess: (HelloCustomerDialog) -> Unit = handleSuccess::invoke
): Unit = loadTouchpoint(
    context = requireContext(),
    config = config,
    onSuccess = onSuccess,
    onError = onError,
    executorService = executorService,
    mainThreadHandler = mainThreadHandler
)

@JvmOverloads
public fun loadTouchpoint(
    context: Context,
    config: HelloCustomerTouchpointConfig,
    executorService: ExecutorService = DefaultExecutorService,
    mainThreadHandler: Handler = DefaultMainThreadHandler,
    onError: (Throwable) -> Unit = handleError::invoke,
    onSuccess: (HelloCustomerDialog) -> Unit = handleSuccess::invoke
): Unit = loadTouchpoint(
    context = context,
    touchpointConfig = config,
    sdkConfig = SdkConfig,
    onError = onError,
    onSuccess = onSuccess,
    executorService = executorService,
    mainThreadHandler = mainThreadHandler
)

//endregion

//region Internal API

internal val DefaultExecutorService: ExecutorService by lazy {
    Executors.newSingleThreadExecutor()
}
internal val DefaultMainThreadHandler: Handler by lazy {
    HandlerCompat.createAsync(Looper.getMainLooper())
}
internal val SdkConfig: SdkConfiguration = HelloCustomerSdkConfig(
    baseApiUrl = "proxy.hellocustomer.dev",
    baseOpinionsUrl = "opinions-development.hellocustomer.com",
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

internal val handleSuccess: (HelloCustomerDialog) -> Unit = {}
internal val handleError: (Throwable) -> Unit = { throwable ->
    SdkLogger.e(throwable, throwable.localizedMessage ?: "")
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

//endregion