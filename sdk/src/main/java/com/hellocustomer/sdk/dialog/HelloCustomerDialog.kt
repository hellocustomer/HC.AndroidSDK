package com.hellocustomer.sdk.dialog

import androidx.fragment.app.FragmentManager

public interface HelloCustomerDialog {

    /**
     * Shows HelloCustomer dialog.
     *
     * @param fragmentManager manager to show and maintain dialog
     * @param tag dialog fragment's TAG
     */
    public fun show(fragmentManager: FragmentManager, tag: String): HelloCustomerDialog

    /**
     * Shows HelloCustomer dialog.
     *
     * @param fragmentManager manager to show and maintain dialog
     */
    public fun show(fragmentManager: FragmentManager): HelloCustomerDialog

    /**
     * Dismisses HelloCustomer dialog.
     */
    public fun dismissDialog(): HelloCustomerDialog
}