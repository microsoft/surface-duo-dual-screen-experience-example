/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store

import androidx.test.rule.ActivityTestRule
import com.microsoft.device.display.sampleheroapp.config.MapConfig.TEST_MODE_ENABLED
import com.microsoft.device.display.sampleheroapp.presentation.MainActivity
import com.microsoft.device.display.sampleheroapp.util.setOrientationRight
import com.microsoft.device.display.sampleheroapp.util.unfreezeRotation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class StoresNavigationSingleTest : BaseStoreNavigationTest() {

    private val activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var ruleChain: RuleChain =
        RuleChain.outerRule(HiltAndroidRule(this)).around(activityRule)

    init {
        TEST_MODE_ENABLED = true
    }

    @After
    fun resetOrientation() {
        unfreezeRotation()
    }

    @Test
    fun openMap_inPortraitMode() {
        openMap_inSingleMode()
    }

    @Test
    fun openMap_inLandscapeMode() {
        setOrientationRight()

        openMap_inSingleMode()
    }

    @Test
    fun openDetailsFromMap_inPortraitMode() {
        openDetailsFromMap_inSingleMode()
    }

    @Test
    fun openDetailsFromMap_inLandscapeMode() {
        setOrientationRight()

        openDetailsFromMap_inSingleMode()
    }

    @Test
    fun openListFromMap_inPortraitMode() {
        openListFromMap_inSingleMode()
    }

    @Test
    fun openListFromMap_inLandscapeMode() {
        setOrientationRight()

        openListFromMap_inSingleMode()
    }

    @Test
    fun openDetailsFromList_inPortraitMode() {
        openDetailsFromList_inSingleMode()
    }

    @Test
    fun openDetailsFromList_inLandscapeMode() {
        setOrientationRight()

        openDetailsFromList_inSingleMode()
    }
}
