package com.android_alex_shtain_assignment.core.navigator

import androidx.appcompat.app.AppCompatActivity
import com.android_alex_shtain_assignment.final.models.FinalArguments

interface NavigatorApi {

    fun showMainFragment(
        activity: AppCompatActivity,
        containerId: Int,
    )

    fun showSettingsFragment(
        activity: AppCompatActivity,
        containerId: Int,
    )

    fun showSalesFragment(
        activity: AppCompatActivity,
        containerId: Int
    )

    fun showFinalFragment(
        activity: AppCompatActivity,
        containerId: Int,
        args: FinalArguments,
    )

    fun showReceiptFragment(
        activity: AppCompatActivity,
        containerId: Int
    )

    fun showRefundFragment(
        activity: AppCompatActivity,
        containerId: Int,
        receiptNumber: String
    )
}