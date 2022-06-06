package com.android_alex_shtain_assignment.core.network.http_client

import com.android_alex_shtain_assignment.core.network.api.FinancialApi

interface HttpClientApi {

    fun getFinancialApi(): FinancialApi

}