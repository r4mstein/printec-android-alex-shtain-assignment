package com.android_alex_shtain_assignment.core.network.repositories.sales

import com.android_alex_shtain_assignment.core.network.NetworkConstants.DEFAULT_ERROR_MESSAGE
import com.android_alex_shtain_assignment.core.network.api.FinancialApi
import com.android_alex_shtain_assignment.core.network.http_client.HttpClientApi
import com.android_alex_shtain_assignment.core.network.network_manager.NetworkManagerApi
import com.android_alex_shtain_assignment.core.network.NetworkStatus
import com.android_alex_shtain_assignment.core.network.repositories.sales.models.PayRequest
import com.android_alex_shtain_assignment.core.network.repositories.sales.models.PayResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.IllegalStateException
import javax.inject.Inject

class SalesRepo @Inject constructor(
    private val httpClient: HttpClientApi,
    private val networkManager: NetworkManagerApi,
) : SalesRepoApi {

//    private val financialApi: FinancialApi by lazy {
//        httpClient.getApi(FinancialApi::class.java)
//    }

    override fun pay(amount: String): Flow<NetworkStatus<PayResponse>> {
        return flow {
            if (networkManager.isConnected().not()) {
                emit(NetworkStatus.NoInternet)
            }
            emit(NetworkStatus.Loading)

            val response = /*financialApi*/httpClient.getFinancialApi().pay(PayRequest(amount))

            emit(NetworkStatus.Success(response))
        }.catch { throwable ->
            emit(NetworkStatus.Error(throwable.message ?: DEFAULT_ERROR_MESSAGE))
        }.flowOn(
            Dispatchers.IO
        )
    }
}