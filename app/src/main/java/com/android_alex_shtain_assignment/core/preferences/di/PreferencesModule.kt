package com.android_alex_shtain_assignment.core.preferences.di

import com.android_alex_shtain_assignment.core.preferences.PreferencesApi
import com.android_alex_shtain_assignment.core.preferences.PreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

    @Binds
    abstract fun providePreferences(preferences: PreferencesImpl): PreferencesApi
}