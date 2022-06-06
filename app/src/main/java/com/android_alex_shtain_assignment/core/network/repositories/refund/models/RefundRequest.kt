package com.android_alex_shtain_assignment.core.network.repositories.refund.models

data class RefundRequest(
    val receipt_number: String,
    val amount: String,
)
