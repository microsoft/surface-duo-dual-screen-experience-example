/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.di

import com.microsoft.device.display.sampleheroapp.ITokenProvider
import com.microsoft.device.display.sampleheroapp.TokenProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TokenModule {

    @Singleton
    @Binds
    abstract fun provideTokenProvider(provider: TokenProvider): ITokenProvider
}
