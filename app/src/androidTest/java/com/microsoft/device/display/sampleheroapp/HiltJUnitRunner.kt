/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.microsoft.device.display.sampleheroapp.config.MapConfig
import com.microsoft.device.dualscreen.ScreenManagerProvider
import com.microsoft.device.dualscreen.fragmentshandler.FragmentManagerStateHandler
import dagger.hilt.android.testing.HiltTestApplication

// A custom runner to set up the instrumented application class for tests.
class HiltJUnitRunner : AndroidJUnitRunner() {

    override fun newApplication(
        classLoader: ClassLoader?,
        name: String?,
        context: Context?
    ): Application =
        super.newApplication(classLoader, HiltTestApplication::class.java.name, context).apply {
            ScreenManagerProvider.init(this)
            FragmentManagerStateHandler.init(this)
            MapConfig.TEST_MODE_ENABLED = true
        }
}
