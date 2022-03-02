/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store

import androidx.test.rule.ActivityTestRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.dualscreen.testing.resetOrientation
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.presentation.MainActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.about.checkAboutInSingleScreenMode
import com.microsoft.device.samples.dualscreenexperience.presentation.about.checkToolbarAbout
import com.microsoft.device.samples.dualscreenexperience.presentation.about.openAbout
import com.microsoft.device.samples.dualscreenexperience.presentation.launch.goBack
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class StoreNavigationSingleScreenTest : BaseStoreNavigationTest() {

    private val activityRule = ActivityTestRule(MainActivity::class.java)
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    var ruleChain: RuleChain =
        RuleChain.outerRule(HiltAndroidRule(this)).around(activityRule)

    @After
    fun resetOrientation() {
        device.resetOrientation()
    }

    @Test
    fun openMapInPortraitMode() {
        openMapInSingleMode()
    }

    @Test
    fun openMapInLandscapeMode() {
        device.setOrientationRight()

        openMapInSingleMode()
    }

    @Test
    fun openAboutInPortraitMode() {
        openMapInSingleMode()

        checkToolbarAbout()
        openAbout()
        checkAboutInSingleScreenMode()

        goBack()

        checkMapFragment()
        checkToolbar(R.string.app_name)
        checkToolbarAbout()
    }

    @Test
    fun openAboutInLandscapeMode() {
        device.setOrientationRight()

        openMapInSingleMode()

        checkToolbarAbout()
        openAbout()
        checkAboutInSingleScreenMode()

        goBack()

        checkMapFragment()
        checkToolbar(R.string.app_name)
        checkToolbarAbout()
    }

    @Test
    fun openDetailsFromMapInPortraitMode() {
        openDetailsFromMapInSingleMode()
    }

    @Test
    fun openDetailsFromMapInLandscapeMode() {
        device.setOrientationRight()

        openDetailsFromMapInSingleMode()
    }

    @Test
    fun openListFromMapInPortraitMode() {
        openListFromMapInSingleMode()
    }

    @Test
    fun openListFromMapInLandscapeMode() {
        device.setOrientationRight()

        openListFromMapInSingleMode()
    }

    @Test
    fun openDetailsFromListInPortraitMode() {
        openDetailsFromListInSingleMode()
    }

    @Test
    fun openDetailsFromListInLandscapeMode() {
        device.setOrientationRight()

        openDetailsFromListInSingleMode()
    }
}
