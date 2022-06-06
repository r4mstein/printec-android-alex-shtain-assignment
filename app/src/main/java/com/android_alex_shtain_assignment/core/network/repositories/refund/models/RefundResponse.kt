package com.android_alex_shtain_assignment.core.network.repositories.refund.models

import com.google.gson.annotations.SerializedName

data class RefundResponse(
    @SerializedName("receiptNumber") val receiptNumber: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("currency") val currency: String,
)