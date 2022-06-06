package com.android_alex_shtain_assignment.core.network.repositories.refund

import com.android_alex_shtain_assignment.core.network.NetworkConstants
import com.android_alex_shtain_assignment.core.network.NetworkStatus
import com.android_alex_shtain_assignment.core.network.http_client.HttpClientApi
import com.android_alex_shtain_assignment.core.network.network_manager.NetworkManagerApi
import com.android_alex_shtain_assignment.core.network.repositories.refund.models.CheckReceiptRequest
import com.android_alex_shtain_assignment.core.network.repositories.refund.models.RefundRequest
import com.android_alex_shtain_assignment.core.network.repositories.refund.models.RefundResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RefundRepo @Inject constructor(
    private val httpClient: HttpClientApi,
    private val networkManager: NetworkManagerApi,
) : RefundRepoApi {

    /**
     * Make a request for checking is the [receiptNumber] valid
     */
    override fun checkReceipt(receiptNumber: String): Flow<NetworkStatus<Int>> {
        return flow {
            if (networkManager.isConnected().not()) {
                emit(NetworkStatus.NoInternet)
            }
            emit(NetworkStatus.Loading)

            val response = httpClient.getFinancialApi().checkReceipt(
                CheckReceiptRequest(receiptNumber)
            )

            if (response.isSuccessful) {
                emit(NetworkStatus.Success(response.code()))
            } else {
                emit(NetworkStatus.Error(NetworkConstants.DEFAULT_ERROR_MESSAGE))
            }
        }.catch { throwable ->
            emit(NetworkStatus.Error(throwable.message ?: NetworkConstants.DEFAULT_ERROR_MESSAGE))
        }.flowOn(
            Dispatchers.IO
        )
    }

    /**
     * Make a request for refund
     *
     * @param receiptNumber - a receipt number for refund
     * @param amount - an amount which needs to be refunded
     */
    override fun refund(
        receiptNumber: String,
        amount: String
    ): Flow<NetworkStatus<RefundResponse>> {
        return flow {
            if (networkManager.isConnected().not()) {
                emit(NetworkStatus.NoInternet)
            }
            emit(NetworkStatus.Loading)

            val response = httpClient.getFinancialApi().refund(
                RefundRequest(
                    receipt_number = receiptNumber,
                    amount = amount,
                )
            )

            emit(NetworkStatus.Success(response))
        }.catch { throwable ->
            emit(NetworkStatus.Error(throwable.message ?: NetworkConstants.DEFAULT_ERROR_MESSAGE))
        }.flowOn(
            Dispatchers.IO
        )
    }
}