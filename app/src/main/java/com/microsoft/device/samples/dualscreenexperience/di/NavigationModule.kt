/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.di

import com.microsoft.device.samples.dualscreenexperience.presentation.MainNavigator
import com.microsoft.device.samples.dualscreenexperience.presentation.about.AboutNavigator
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.DevModeNavigator
import com.microsoft.device.samples.dualscreenexperience.presentation.history.HistoryNavigator
import com.microsoft.device.samples.dualscreenexperience.presentation.launch.LaunchNavigator
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderNavigator
import com.microsoft.device.samples.dualscreenexperience.presentation.product.ProductNavigator
import com.microsoft.device.samples.dualscreenexperience.presentation.store.StoreNavigator
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
    fun provideMainNavigator(): MainNavigator = MainNavigator()

    @Provides
    fun provideStoreNavigator(navigator: MainNavigator): StoreNavigator = navigator

    @Provides
    fun provideProductNavigator(navigator: MainNavigator): ProductNavigator = navigator

    @Provides
    fun provideOrderNavigator(navigator: MainNavigator): OrderNavigator = navigator

    @Provides
    fun provideHistoryNavigator(navigator: MainNavigator): HistoryNavigator = navigator

    @Provides
    @Singleton
    fun provideDevModeNavigator(): DevModeNavigator = DevModeNavigator()

    @Provides
    @Singleton
    fun provideAboutNavigator(): AboutNavigator = AboutNavigator()
}
