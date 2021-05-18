/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.di

import android.content.Context
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialBalloon
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TutorialModule {

    @Singleton
    @Provides
    fun provideTutorialComponent(@ApplicationContext appContext: Context): TutorialBalloon =
        TutorialBalloon(appContext)
}
