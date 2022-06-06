package com.android_alex_shtain_assignment.core.navigator

import androidx.appcompat.app.AppCompatActivity
import com.android_alex_shtain_assignment.core.extensions.addFragment
import com.android_alex_shtain_assignment.core.extensions.replaceFragmentAndAddToBackStack
import com.android_alex_shtain_assignment.final.FinalFragment
import com.android_alex_shtain_assignment.final.models.FinalArguments
import com.android_alex_shtain_assignment.main.MainFragment
import com.android_alex_shtain_assignment.refund.amount.RefundAmountFragment
import com.android_alex_shtain_assignment.refund.receipt.ReceiptFragment
import com.android_alex_shtain_assignment.sales.SalesFragment
import com.android_alex_shtain_assignment.settings.SettingsFragment
import javax.inject.Inject

/**
 * [NavigatorImpl] - class with methods which allows to navigate to different fragments
 */
class NavigatorImpl @Inject constructor() : NavigatorApi {

    override fun showMainFragment(activity: AppCompatActivity, containerId: Int) {
        activity.addFragment(
            containerId = containerId,
            fragment = MainFragment.newInstance(),
        )
    }

    override fun showSettingsFragment(activity: AppCompatActivity, containerId: Int) {
        activity.replaceFragmentAndAddToBackStack(
            containerId = containerId,
            fragment = SettingsFragment.newInstance(),
        )
    }

    override fun showSalesFragment(activity: AppCompatActivity, containerId: Int) {
        activity.replaceFragmentAndAddToBackStack(
            containerId = containerId,
            fragment = SalesFragment.newInstance(),
        )
    }

    override fun showFinalFragment(
        activity: AppCompatActivity,
        containerId: Int,
        args: FinalArguments,
    ) {
        activity.replaceFragmentAndAddToBackStack(
            containerId = containerId,
            fragment = FinalFragment.newInstance(args),
        )
    }

    override fun showReceiptFragment(activity: AppCompatActivity, containerId: Int) {
        activity.replaceFragmentAndAddToBackStack(
            containerId = containerId,
            fragment = ReceiptFragment.newInstance(),
        )
    }

    override fun showRefundFragment(
        activity: AppCompatActivity,
        containerId: Int,
        receiptNumber: String
    ) {
        activity.replaceFragmentAndAddToBackStack(
            containerId = containerId,
            fragment = RefundAmountFragment.newInstance(receiptNumber),
        )
    }
}