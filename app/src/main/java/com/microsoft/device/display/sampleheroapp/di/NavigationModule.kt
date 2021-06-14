/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.di

import com.microsoft.device.display.sampleheroapp.presentation.AppNavigator
import com.microsoft.device.display.sampleheroapp.presentation.devmode.DevModeNavigator
import com.microsoft.device.display.sampleheroapp.presentation.launch.LaunchNavigator
import com.microsoft.device.display.sampleheroapp.presentation.order.OrderNavigator
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
    fun provideLaunchNavigator(): LaunchNavigator = LaunchNavigator()

    @Provides
    @Singleton
    fun provideMainNavigator(): AppNavigator = AppNavigator()

    @Provides
    fun provideStoreNavigator(navigator: AppNavigator): StoreNavigator = navigator

    @Provides
    fun provideProductNavigator(navigator: AppNavigator): ProductNavigator = navigator

    @Provides
    fun provideOrderNavigator(navigator: AppNavigator): OrderNavigator = navigator

    @Provides
    @Singleton
    fun provideDevModeNavigator(): DevModeNavigator = DevModeNavigator()
}
