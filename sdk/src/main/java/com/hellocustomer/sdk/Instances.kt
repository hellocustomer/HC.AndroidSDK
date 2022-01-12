package com.hellocustomer.sdk

import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat
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

internal object Instances {

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
}