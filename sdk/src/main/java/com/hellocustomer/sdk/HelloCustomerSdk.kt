@file:JvmName("HelloCustomer")

package com.hellocustomer.sdk

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.GsonBuilder
import com.hellocustomer.sdk.dialog.HelloCustomerBottomSheetDialog
import com.hellocustomer.sdk.dialog.HelloCustomerDialog
import com.hellocustomer.sdk.dialog.HelloCustomerDialogImpl
import com.hellocustomer.sdk.exception.DefaultTranslationNotFoundException
import com.hellocustomer.sdk.logger.DefaultLogger
import com.hellocustomer.sdk.mapper.TouchpointMapper
import com.hellocustomer.sdk.network.HelloCustomerApi
import com.hellocustomer.sdk.network.HelloCustomerApiImpl
import com.hellocustomer.sdk.network.dto.DialogTypeDto
import com.hellocustomer.sdk.network.dto.TouchpointDto
import com.hellocustomer.sdk.network.dto.TranslationDto
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale
import kotlin.coroutines.CoroutineContext

//region Public API

@JvmOverloads
public fun AppCompatActivity.createTouchpoint(
    token: String,
    onError: (Throwable) -> Unit = ::handleException,
    onSuccess: (HelloCustomerDialog) -> Unit = {}
): Unit = createTouchpoint(
    token = token,
    fragmentManager = supportFragmentManager,
    onSuccess = onSuccess,
    onError = onError
)

@JvmOverloads
public fun Fragment.createTouchpoint(
    token: String,
    onError: (Throwable) -> Unit = ::handleException,
    onSuccess: (HelloCustomerDialog) -> Unit = {}
): Unit = createTouchpoint(
    token = token,
    fragmentManager = childFragmentManager,
    onSuccess = onSuccess,
    onError = onError
)

@JvmOverloads
public fun createTouchpoint(
    token: String,
    fragmentManager: FragmentManager,
    onError: (Throwable) -> Unit = ::handleException,
    onSuccess: (HelloCustomerDialog) -> Unit = {}
): Unit = runBlocking {
    launch(context = Dispatchers.IO + CoroutineExceptionHandler(::handleCoroutineException)) {
        SdkApi.getTouchpoint(token)
            .mapCatching { touchpoint: TouchpointDto ->

                val lang: TranslationDto = touchpoint.findDefaultLanguage(Locale.getDefault())
                    ?: throw DefaultTranslationNotFoundException()

                val config = TouchpointMapper.toConfig(touchpoint, lang)

                val dialog: HelloCustomerDialog = when (touchpoint.dialogType) {
                    DialogTypeDto.BOTTOM -> HelloCustomerBottomSheetDialog.newInstance(config)
                    DialogTypeDto.CENTER -> HelloCustomerDialogImpl.newInstance(config)
                }

                dialog.show(fragmentManager)
            }
            .onFailure(onError)
            .onSuccess(onSuccess)
    }
}

//endregion

//region Internal API

internal val SdkApi: HelloCustomerApi = HelloCustomerApiImpl(
    gson = GsonBuilder().create()
)

internal val SdkLogger = DefaultLogger(tag = "HELLO_CUSTOMER")

internal fun handleException(throwable: Throwable) {
    SdkLogger.e(throwable, throwable.localizedMessage ?: "")
}

internal fun handleCoroutineException(context: CoroutineContext, throwable: Throwable) {
    SdkLogger.e(throwable, throwable.localizedMessage ?: "Coroutine exception.")
}

//endregion