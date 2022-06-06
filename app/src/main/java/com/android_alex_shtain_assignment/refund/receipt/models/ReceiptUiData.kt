package com.android_alex_shtain_assignment.refund.receipt.models

data class ReceiptUiData(
    val toolbarTitle: Int,
    val toolbarIcon: Int,
    val numberHint: Int,
    val btnNextLabel: Int,
    val btnNextClickListener: (receipt: String) -> Unit,
    val isShowLastReceiptBtn: Boolean,
    val btnLastReceiptLabel: Int,
    val btnLastReceiptClickListener: () -> Unit,
)
