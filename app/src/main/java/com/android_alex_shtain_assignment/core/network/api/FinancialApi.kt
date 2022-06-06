package com.android_alex_shtain_assignment.core.network.api

import com.android_alex_shtain_assignment.core.network.repositories.refund.models.CheckReceiptRequest
import com.android_alex_shtain_assignment.core.network.repositories.refund.models.RefundRequest
import com.android_alex_shtain_assignment.core.network.repositories.refund.models.RefundResponse
import com.android_alex_shtain_assignment.core.network.repositories.sales.models.PayRequest
import com.android_alex_shtain_assignment.core.network.repositories.sales.models.PayResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface FinancialApi {

    @POST("/api/printec/financial/pay")
    suspend fun pay(@Body body: PayRequest): PayResponse

    @POST("/api/printec/financial/refund/receipt-number")
    suspend fun checkReceipt(@Body body: CheckReceiptRequest): Response<Void>

    @POST("/api/printec/financial/refund")
    suspend fun refund(@Body body: RefundRequest): RefundResponse
}