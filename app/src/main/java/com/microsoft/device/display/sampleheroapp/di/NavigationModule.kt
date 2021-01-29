/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.di

import android.content.Context
import com.microsoft.device.display.sampleheroapp.presentation.AppNavigator
import com.microsoft.device.display.sampleheroapp.presentation.product.ProductNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideMainNavigator(@ApplicationContext appContext: Context): AppNavigator =
        AppNavigator(appContext)

    @Provides
    fun provideProductNavigator(navigator: AppNavigator): ProductNavigator = navigator
}
