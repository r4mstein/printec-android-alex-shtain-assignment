package com.android_alex_shtain_assignment.refund.receipt.status

sealed class ReceiptActionStatus {

    object Initial : ReceiptActionStatus()

    class ShowNextFragment(val receipt: String) : ReceiptActionStatus()

}
