package com.hellocustomer.sdk.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hellocustomer.sdk.utility.LiveEvent

internal class HelloCustomerViewModel : ViewModel() {

    private val _navigation = LiveEvent<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    fun onEvaluate(url: String) {
        _navigation.postValue(Navigation.WebPage(url))
    }

    sealed class Navigation {
        data class WebPage(val url: String) : Navigation()
    }
}