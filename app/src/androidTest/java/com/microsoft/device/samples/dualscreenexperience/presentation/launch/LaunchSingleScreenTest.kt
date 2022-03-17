/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.launch

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.dualscreen.testing.resetOrientation
import com.microsoft.device.samples.dualscreenexperience.config.SharedPrefConfig.PREF_NAME
import com.microsoft.device.samples.dualscreenexperience.presentation.store.checkMapFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class LaunchSingleScreenTest {

    private val activityRule = ActivityTestRule(LaunchActivity::class.java)
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    var ruleChain: RuleChain =
        RuleChain.outerRule(HiltAndroidRule(this)).around(activityRule)

    init {
        resetSharedPrefs()
    }

    private fun resetSharedPrefs() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPref.edit().clear().commit()
    }

    @After
    fun resetOrientation() {
        device.resetOrientation()
    }

    @Test
    fun openLaunchInPortraitMode() {
        checkLaunchInSingleMode()
    }

    @Test
    fun openLaunchInLandscapeMode() {
        device.setOrientationRight()

        checkLaunchInSingleMode()
    }

    @Test
    fun openMainInPortraitMode() {
        checkSingleLaunchButton()
        clickSingleLaunchButton()

        checkMapFragment()
        goBack()

        checkLaunchInSingleMode()
    }

    @Test
    fun openMainInLandscapeMode() {
        device.setOrientationRight()

        checkSingleLaunchButton()
        clickSingleLaunchButton()

        checkMapFragment()
        goBack()

        checkLaunchInSingleMode()
    }
}
