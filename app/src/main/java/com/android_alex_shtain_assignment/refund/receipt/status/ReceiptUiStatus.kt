package com.android_alex_shtain_assignment.refund.receipt.status

import com.android_alex_shtain_assignment.refund.receipt.models.ReceiptUiData

sealed class ReceiptUiStatus {

    object Initial : ReceiptUiStatus()

    class SetupUI(val data: ReceiptUiData) : ReceiptUiStatus()

    class ShowResourcesError(val error: Int) : ReceiptUiStatus()

    class ShowError(val error: String) : ReceiptUiStatus()

    object ShowLoader : ReceiptUiStatus()

    class ShowLastReceipt(val receipt: String) : ReceiptUiStatus()

}
