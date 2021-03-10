/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.di

import com.microsoft.device.display.sampleheroapp.presentation.AppNavigator
import com.microsoft.device.display.sampleheroapp.presentation.product.ProductNavigator
import com.microsoft.device.display.sampleheroapp.presentation.store.StoreNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideMainNavigator(): AppNavigator = AppNavigator()

    @Provides
    fun provideProductNavigator(navigator: AppNavigator): ProductNavigator = navigator

    @Provides
    fun provideStoreNavigator(navigator: AppNavigator): StoreNavigator = navigator
}
