package com.android_alex_shtain_assignment.core.network.repositories.di

import com.android_alex_shtain_assignment.core.network.repositories.sales.SalesRepo
import com.android_alex_shtain_assignment.core.network.repositories.sales.SalesRepoApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SalesRepoModule {

    @Binds
    abstract fun provideSalesRepo(repo: SalesRepo): SalesRepoApi
}