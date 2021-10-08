package com.hellocustomer.sdk.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hellocustomer.sdk.utility.LiveEvent

internal class HelloCustomerViewModel(
    private val config: HelloCustomerDialogConfig
) : ViewModel() {

    private val _navigation = LiveEvent<Navigation>()
    val navigation: LiveData<Navigation> = _navigation

    fun onEvaluate(score: Int) {
        val url = config.surveyUriBuilder.build(score)
        _navigation.value = Navigation.WebPage(url)
        _navigation.value = Navigation.Close
    }

    fun onCloseButtonClick() {
        _navigation.postValue(Navigation.Close)
    }

    sealed class Navigation {
        data class WebPage(val url: String) : Navigation()
        object Close : Navigation()
    }
}