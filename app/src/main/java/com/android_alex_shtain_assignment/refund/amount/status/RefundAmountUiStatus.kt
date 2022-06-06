package com.android_alex_shtain_assignment.refund.amount.status

import com.android_alex_shtain_assignment.refund.amount.models.RefundAmountUiData

sealed class RefundAmountUiStatus {

    object Initial : RefundAmountUiStatus()

    class SetupUI(val data: RefundAmountUiData) : RefundAmountUiStatus()

    class ShowResourcesError(val error: Int) : RefundAmountUiStatus()

    class ShowError(val error: String) : RefundAmountUiStatus()

    object ShowLoader : RefundAmountUiStatus()

}
