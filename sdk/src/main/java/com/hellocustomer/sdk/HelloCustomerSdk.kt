@file:JvmName("HelloCustomer")

package com.hellocustomer.sdk

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hellocustomer.sdk.dialog.DialogType
import com.hellocustomer.sdk.dialog.HelloCustomerBottomSheetDialog
import com.hellocustomer.sdk.dialog.HelloCustomerDialog
import com.hellocustomer.sdk.dialog.HelloCustomerDialogImpl
import com.hellocustomer.sdk.exception.DefaultTranslationNotFoundException
import com.hellocustomer.sdk.logger.DefaultLogger
import com.hellocustomer.sdk.mapper.TouchpointMapper
import com.hellocustomer.sdk.network.HelloCustomerApi
import com.hellocustomer.sdk.network.HelloCustomerApiImpl
import com.hellocustomer.sdk.network.dto.TouchpointDto
import com.hellocustomer.sdk.network.dto.TranslationDto
import com.squareup.moshi.Moshi
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
    dialogType: DialogType = DialogType.CENTER,
    onError: (Throwable) -> Unit = ::handleException,
    onSuccess: (HelloCustomerDialog) -> Unit = {}
): Unit = createTouchpoint(
    token = token,
    dialogType = dialogType,
    fragmentManager = supportFragmentManager,
    onSuccess = onSuccess,
    onError = onError
)

@JvmOverloads
public fun Fragment.createTouchpoint(
    token: String,
    dialogType: DialogType = DialogType.CENTER,
    onError: (Throwable) -> Unit = ::handleException,
    onSuccess: (HelloCustomerDialog) -> Unit = {}
): Unit = createTouchpoint(
    token = token,
    dialogType = dialogType,
    fragmentManager = childFragmentManager,
    onSuccess = onSuccess,
    onError = onError
)

/**
 * @param token
 * @param fragmentManager
 * @param dialogType
 * @param onError
 * @param onSuccess
 */
@JvmOverloads
public fun createTouchpoint(
    token: String,
    fragmentManager: FragmentManager,
    dialogType: DialogType = DialogType.CENTER,
    onError: (Throwable) -> Unit = ::handleException,
    onSuccess: (HelloCustomerDialog) -> Unit = {}
): Unit = runBlocking {
    launch(context = Dispatchers.IO + CoroutineExceptionHandler(::handleCoroutineException)) {
        SdkApi.getTouchpoint(token)
            .mapCatching { touchpoint: TouchpointDto ->

                val lang: TranslationDto = touchpoint.findDefaultLanguage(Locale.getDefault())
                    ?: throw DefaultTranslationNotFoundException()

                val config = TouchpointMapper.toConfig(touchpoint, lang)

                val dialog: HelloCustomerDialog = when (dialogType) {
                    DialogType.BOTTOM -> HelloCustomerBottomSheetDialog.newInstance(config)
                    DialogType.CENTER -> HelloCustomerDialogImpl.newInstance(config)
                }

                dialog.show(fragmentManager)
            }
            .onFailure(onError)
            .onSuccess(onSuccess)
    }
}

//endregion

//region Internal API

internal val MoshiInstance: Moshi = Moshi.Builder().build()

internal val SdkApi: HelloCustomerApi = HelloCustomerApiImpl(
    touchpointAdapter = MoshiInstance.adapter(TouchpointDto::class.java)
)

internal val SdkLogger = DefaultLogger(tag = "HELLO_CUSTOMER")

internal fun handleException(throwable: Throwable) {
    SdkLogger.e(throwable, throwable.localizedMessage ?: "")
}

internal fun handleCoroutineException(context: CoroutineContext, throwable: Throwable) {
    SdkLogger.e(throwable, throwable.localizedMessage ?: "Coroutine exception.")
}

//endregion