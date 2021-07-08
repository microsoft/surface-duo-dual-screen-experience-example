/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp

import android.app.Application
import com.microsoft.device.dualscreen.ScreenManagerProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class HeroApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startDualScreenSDK()
    }

    private fun startDualScreenSDK() {
        ScreenManagerProvider.init(this)
    }
}
