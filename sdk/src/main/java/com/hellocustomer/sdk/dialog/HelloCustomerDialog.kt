package com.hellocustomer.sdk.dialog

import androidx.fragment.app.FragmentManager

internal interface HelloCustomerDialog {

    fun show(fragmentManager: FragmentManager, tag: String): HelloCustomerDialog

    fun show(fragmentManager: FragmentManager): HelloCustomerDialog
}