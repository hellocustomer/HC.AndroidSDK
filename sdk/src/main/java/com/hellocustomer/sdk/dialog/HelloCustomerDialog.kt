package com.hellocustomer.sdk.dialog

import androidx.fragment.app.FragmentManager

public interface HelloCustomerDialog {

    public fun show(fragmentManager: FragmentManager, tag: String): HelloCustomerDialog

    public fun show(fragmentManager: FragmentManager): HelloCustomerDialog

    public fun dismissDialog(): HelloCustomerDialog
}