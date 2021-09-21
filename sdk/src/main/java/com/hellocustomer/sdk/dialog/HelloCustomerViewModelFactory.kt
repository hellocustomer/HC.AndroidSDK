package com.hellocustomer.sdk.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

internal class HelloCustomerViewModelFactory(
    private val config: HelloCustomerDialogConfig
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HelloCustomerViewModel::class.java)) {
            return HelloCustomerViewModel(config) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}