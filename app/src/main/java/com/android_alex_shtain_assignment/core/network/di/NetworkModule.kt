package com.android_alex_shtain_assignment.core.network.di

import com.android_alex_shtain_assignment.core.network.http_client.HttpClientApi
import com.android_alex_shtain_assignment.core.network.http_client.HttpClientImpl
import com.android_alex_shtain_assignment.core.network.network_manager.NetworkManagerApi
import com.android_alex_shtain_assignment.core.network.network_manager.NetworkManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun provideHttpClient(httpClient: HttpClientImpl): HttpClientApi

    @Binds
    abstract fun provideNetworkManager(manager: NetworkManagerImpl): NetworkManagerApi
}