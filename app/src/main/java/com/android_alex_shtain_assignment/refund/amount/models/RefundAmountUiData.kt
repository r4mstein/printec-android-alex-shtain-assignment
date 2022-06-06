package com.android_alex_shtain_assignment.refund.amount.models

data class RefundAmountUiData(
    val toolbarTitle: Int,
    val toolbarIcon: Int,
    val amountHint: Int,
    val btnNextLabel: Int,
    val btnNextClickListener: (receipt: String, amount: String) -> Unit,
)