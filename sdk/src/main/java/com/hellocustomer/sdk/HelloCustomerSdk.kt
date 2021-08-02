@file:JvmName("HelloCustomer")

package com.hellocustomer.sdk

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hellocustomer.sdk.dialog.HelloCustomerBottomSheetDialog
import com.hellocustomer.sdk.dialog.HelloCustomerDialog
import com.hellocustomer.sdk.dialog.HelloCustomerDialogImpl
import com.hellocustomer.sdk.exception.DefaultTranslationNotFoundException
import com.hellocustomer.sdk.mapper.TouchpointMapper
import com.hellocustomer.sdk.network.HelloCustomerApi
import com.hellocustomer.sdk.network.HelloCustomerApiImpl
import com.hellocustomer.sdk.network.dto.DialogTypeDto
import com.hellocustomer.sdk.network.dto.TouchpointDto
import com.hellocustomer.sdk.network.dto.TranslationDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale

/**
 * @throws DefaultTranslationNotFoundException if config does not have a default translation
 */
public fun AppCompatActivity.createTouchpoint(token: String): Unit =
    createTouchpoint(token, supportFragmentManager)

/**
 * @throws DefaultTranslationNotFoundException if config does not have a default translation
 */
public fun Fragment.createTouchpoint(token: String): Unit =
    createTouchpoint(token, childFragmentManager)

/**
 * @throws DefaultTranslationNotFoundException if config does not have a default translation
 */
public fun createTouchpoint(token: String, fragmentManager: FragmentManager): Unit = runBlocking {
    val api: HelloCustomerApi = HelloCustomerApiImpl()

    launch(context = Dispatchers.IO) {
        val touchpoint: TouchpointDto = api.getTouchpoint(token)

        val lang: TranslationDto = touchpoint.findDefaultLanguage(Locale.getDefault())
            ?: throw DefaultTranslationNotFoundException()

        val config = TouchpointMapper.toConfig(touchpoint, lang)

        val dialog: HelloCustomerDialog = when (touchpoint.dialogType) {
            DialogTypeDto.BOTTOM -> HelloCustomerBottomSheetDialog.newInstance(config)
            DialogTypeDto.CENTER -> HelloCustomerDialogImpl.newInstance(config)
        }

        dialog.show(fragmentManager)
    }
}