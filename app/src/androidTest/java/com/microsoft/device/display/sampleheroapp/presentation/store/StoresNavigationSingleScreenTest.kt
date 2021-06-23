/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store

import androidx.test.rule.ActivityTestRule
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.presentation.MainActivity
import com.microsoft.device.display.sampleheroapp.presentation.about.checkAboutInSingleScreenMode
import com.microsoft.device.display.sampleheroapp.presentation.about.checkToolbarAbout
import com.microsoft.device.display.sampleheroapp.presentation.about.openAbout
import com.microsoft.device.display.sampleheroapp.presentation.launch.goBack
import com.microsoft.device.display.sampleheroapp.util.setOrientationRight
import com.microsoft.device.display.sampleheroapp.util.unfreezeRotation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class StoresNavigationSingleScreenTest : BaseStoreNavigationTest() {

    private val activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var ruleChain: RuleChain =
        RuleChain.outerRule(HiltAndroidRule(this)).around(activityRule)

    @After
    fun resetOrientation() {
        unfreezeRotation()
    }

    @Test
    fun openMapInPortraitMode() {
        openMapInSingleMode()
    }

    @Test
    fun openMapInLandscapeMode() {
        setOrientationRight()

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
        setOrientationRight()

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
        setOrientationRight()

        openDetailsFromMapInSingleMode()
    }

    @Test
    fun openListFromMapInPortraitMode() {
        openListFromMapInSingleMode()
    }

    @Test
    fun openListFromMapInLandscapeMode() {
        setOrientationRight()

        openListFromMapInSingleMode()
    }

    @Test
    fun openDetailsFromListInPortraitMode() {
        openDetailsFromListInSingleMode()
    }

    @Test
    fun openDetailsFromListInLandscapeMode() {
        setOrientationRight()

        openDetailsFromListInSingleMode()
    }
}
