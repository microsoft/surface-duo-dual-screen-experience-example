/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.di

import android.content.Context
import android.content.SharedPreferences
import com.microsoft.device.samples.dualscreenexperience.common.prefs.PreferenceManager
import com.microsoft.device.samples.dualscreenexperience.common.prefs.TutorialPreferences
import com.microsoft.device.samples.dualscreenexperience.config.SharedPrefConfig.PREF_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {

    @Singleton
    @Provides
    fun provideSharedPref(@ApplicationContext appContext: Context): SharedPreferences =
        appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideTutorialPreferences(prefManager: PreferenceManager): TutorialPreferences = prefManager
}
