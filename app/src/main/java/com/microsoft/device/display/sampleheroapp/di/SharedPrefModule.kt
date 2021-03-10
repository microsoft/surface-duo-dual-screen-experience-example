/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.di

import android.content.Context
import android.content.SharedPreferences
import com.microsoft.device.display.sampleheroapp.config.LocalStorageConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefModule {

    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(LocalStorageConfig.PREF_NAME, Context.MODE_PRIVATE)
}
