package com.android_alex_shtain_assignment.core.network.repositories.sales

import com.android_alex_shtain_assignment.core.network.NetworkStatus
import com.android_alex_shtain_assignment.core.network.repositories.sales.models.PayResponse
import kotlinx.coroutines.flow.Flow

interface SalesRepoApi {

    fun pay(amount: String): Flow<NetworkStatus<PayResponse>>
}