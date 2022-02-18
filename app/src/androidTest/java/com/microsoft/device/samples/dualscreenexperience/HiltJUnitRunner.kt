/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience

import android.app.Application
import android.content.Context
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.runner.AndroidJUnitRunner
import com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckResultUtils.matchesCheckNames
import com.microsoft.device.samples.dualscreenexperience.config.MapConfig
import dagger.hilt.android.testing.HiltTestApplication
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.Matchers.`is` as iz

// A custom runner to set up the instrumented application class for tests.
class HiltJUnitRunner : AndroidJUnitRunner() {

    override fun newApplication(
        classLoader: ClassLoader?,
        name: String?,
        context: Context?
    ): Application =
        super.newApplication(classLoader, HiltTestApplication::class.java.name, context).apply {
            MapConfig.TEST_MODE_ENABLED = true
            AccessibilityChecks.enable().apply {
                setSuppressingResultMatcher(
                    allOf(
                        // Currently the FoldableNavigationComponent adds fragments on top of each other
                        matchesCheckNames(iz("DuplicateClickableBoundsCheck"))
                    )
                )
            }
        }
}
