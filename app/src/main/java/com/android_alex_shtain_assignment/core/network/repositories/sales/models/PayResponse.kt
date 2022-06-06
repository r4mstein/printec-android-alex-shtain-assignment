package com.android_alex_shtain_assignment.core.network.repositories.sales.models

data class PayResponse(
    val receiptNumber: String,
    val amount: String,
    val currency: String,
)
