package com.android_alex_shtain_assignment.refund.amount.status

import com.android_alex_shtain_assignment.final.models.FinalArguments

sealed class RefundAmountActionStatus {

    object Initial : RefundAmountActionStatus()

    class ShowFinalFragment(
        val args: FinalArguments,
        val descriptionPrefix: Int,
    ) : RefundAmountActionStatus()

}
