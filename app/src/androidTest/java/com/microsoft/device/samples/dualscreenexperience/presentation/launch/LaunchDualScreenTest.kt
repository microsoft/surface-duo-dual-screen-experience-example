/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.launch

import androidx.test.rule.ActivityTestRule
import com.microsoft.device.dualscreen.ScreenManagerProvider
import com.microsoft.device.samples.dualscreenexperience.presentation.store.checkMapFragment
import com.microsoft.device.samples.dualscreenexperience.util.setOrientationRight
import com.microsoft.device.samples.dualscreenexperience.util.switchFromDualToSingleScreen
import com.microsoft.device.samples.dualscreenexperience.util.switchFromSingleToDualScreen
import com.microsoft.device.samples.dualscreenexperience.util.unfreezeRotation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class LaunchDualScreenTest {

    private val activityRule = ActivityTestRule(LaunchActivity::class.java)

    @get:Rule
    var ruleChain: RuleChain =
        RuleChain.outerRule(HiltAndroidRule(this)).around(activityRule)

    @After
    fun resetOrientation() {
        unfreezeRotation()
        ScreenManagerProvider.getScreenManager().clear()
    }

    @Test
    fun openLaunchInDualPortraitMode() {
        switchFromSingleToDualScreen()

        checkLaunchInDualMode()

        switchFromDualToSingleScreen()
        checkTutorialNotShowing()
    }

    @Test
    fun openLaunchInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        checkLaunchInDualMode()

        switchFromDualToSingleScreen()
        checkTutorialNotShowing()
    }

    @Test
    fun openMainInDualPortraitMode() {
        switchFromSingleToDualScreen()

        checkDualLaunchButton()
        clickDualLaunchButton()

        checkMapFragment()
        goBack()

        checkLaunchInDualMode()
    }

    @Test
    fun openMainInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        checkDualLaunchButton()
        clickDualLaunchButton()

        checkMapFragment()
        goBack()

        checkLaunchInDualMode()
    }

    @Test
    fun spanMain() {
        checkSingleLaunchButton()
        clickSingleLaunchButton()

        checkMapFragment()
        switchFromSingleToDualScreen()
        goBack()

        checkLaunchInDualMode()
    }
}
