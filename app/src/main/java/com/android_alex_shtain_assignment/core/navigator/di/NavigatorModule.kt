package com.android_alex_shtain_assignment.core.navigator.di

import com.android_alex_shtain_assignment.core.navigator.NavigatorApi
import com.android_alex_shtain_assignment.core.navigator.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigatorModule {

    @Binds
    abstract fun provideNavigator(navigator: NavigatorImpl): NavigatorApi
}