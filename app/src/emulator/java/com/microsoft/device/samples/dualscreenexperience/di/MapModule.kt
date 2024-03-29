/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.di

import com.microsoft.device.samples.dualscreenexperience.presentation.store.map.BingMapController
import com.microsoft.device.samples.dualscreenexperience.presentation.store.map.MapController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapModule {

    @Singleton
    @Binds
    abstract fun provideMapController(mapController: BingMapController): MapController
}
