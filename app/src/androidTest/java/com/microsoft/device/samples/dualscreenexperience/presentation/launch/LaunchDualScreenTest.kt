/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.launch

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.dualscreen.testing.isSurfaceDuo
import com.microsoft.device.dualscreen.testing.resetOrientation
import com.microsoft.device.dualscreen.testing.spanFromStart
import com.microsoft.device.dualscreen.testing.unspanToStart
import com.microsoft.device.samples.dualscreenexperience.presentation.store.checkMapFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class LaunchDualScreenTest {

    private val activityRule = ActivityTestRule(LaunchActivity::class.java)
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    var ruleChain: RuleChain =
        RuleChain.outerRule(HiltAndroidRule(this)).around(activityRule)

    @After
    fun resetOrientation() {
        device.resetOrientation()
    }

    @Test
    fun openLaunchInDualPortraitMode() {
        device.spanFromStart()

        checkLaunchInDualMode()

        device.unspanToStart()
        checkTutorialNotShowing()
    }

    @Test
    fun openLaunchInDualLandscapeMode() {
        device.spanFromStart()
        device.setOrientationRight()

        checkLaunchInDualMode()

        device.unspanToStart()
        checkTutorialNotShowing()
    }

    @Test
    fun openMainInDualPortraitMode() {
        device.spanFromStart()

        checkDualLaunchButton()
        clickDualLaunchButton()

        checkMapFragment()
        goBack()

        checkLaunchInDualMode()
    }

    @Test
    fun openMainInDualLandscapeMode() {
        device.spanFromStart()
        device.setOrientationRight()

        checkDualLaunchButton()
        clickDualLaunchButton()

        checkMapFragment()
        goBack()

        checkLaunchInDualMode()
    }

    @Test
    fun spanMain() {
        if (device.isSurfaceDuo()) {
            checkSingleLaunchButton()
            clickSingleLaunchButton()
        } else {
            checkDualLaunchButton()
            clickDualLaunchButton()
        }

        checkMapFragment()
        device.spanFromStart()
        goBack()

        checkLaunchInDualMode()
    }
}
