package com.android_alex_shtain_assignment.core.network.repositories.di

import com.android_alex_shtain_assignment.core.network.repositories.refund.RefundRepo
import com.android_alex_shtain_assignment.core.network.repositories.refund.RefundRepoApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RefundRepoModule {

    @Binds
    abstract fun provideRefundRepo(repo: RefundRepo): RefundRepoApi
}