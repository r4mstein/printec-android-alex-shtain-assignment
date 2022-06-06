package com.android_alex_shtain_assignment.core.network.repositories.refund

import com.android_alex_shtain_assignment.core.network.NetworkStatus
import com.android_alex_shtain_assignment.core.network.repositories.refund.models.RefundResponse
import kotlinx.coroutines.flow.Flow

interface RefundRepoApi {

    fun checkReceipt(receiptNumber: String): Flow<NetworkStatus<Int>>

    fun refund(
        receiptNumber: String,
        amount: String
    ): Flow<NetworkStatus<RefundResponse>>
}