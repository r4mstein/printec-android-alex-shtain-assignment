package com.android_alex_shtain_assignment.sales.models

data class SalesUiData(
    val toolbarTitle: Int,
    val toolbarIcon: Int,
    val amountHint: Int,
    val amountTextChangeListener: (amount: String) -> Unit,
    val btnNextLabel: Int,
    val btnNextClickListener: (amount: String) -> Unit,
)
