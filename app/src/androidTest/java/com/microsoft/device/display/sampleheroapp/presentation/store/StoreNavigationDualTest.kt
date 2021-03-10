/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store

import androidx.test.rule.ActivityTestRule
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.config.MapConfig.TEST_MODE_ENABLED
import com.microsoft.device.display.sampleheroapp.presentation.MainActivity
import com.microsoft.device.display.sampleheroapp.util.setOrientationRight
import com.microsoft.device.display.sampleheroapp.util.switchFromSingleToDualScreen
import com.microsoft.device.display.sampleheroapp.util.unfreezeRotation
import com.microsoft.device.dualscreen.ScreenManagerProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class StoreNavigationDualTest : BaseStoreNavigationTest() {

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
        ScreenManagerProvider.getScreenManager().clear()
    }

    @Test
    fun openMapInDualLandscapeMode() {
        switchFromSingleToDualScreen()

        openMapInSingleMode()
    }

    @Test
    fun openMapInDualPortraitMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        openMapInSingleMode()
    }

    @Test
    fun openDetailsFromMapInDualLandscapeMode() {
        openDetailsFromMapInDualMode()
    }

    @Test
    fun openDetailsFromMapInDualPortraitMode() {
        setOrientationRight()
        openDetailsFromMapInDualMode()
    }

    @Test
    fun spanDetailsFromMap() {
        clickOnMapMarker(storeMehul.name)

        switchFromSingleToDualScreen()
        checkMapFragment()
        checkDetailsFragment(storeMehul)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    @Test
    fun openListFromMapInDualLandscapeMode() {
        openListFromMapInDualMode()
    }

    @Test
    fun openListFromMapInDualPortraitMode() {
        setOrientationRight()
        openListFromMapInDualMode()
    }

    @Test
    fun spanListFromMap() {
        clickOnMapMarker(cityRedmond.name)

        switchFromSingleToDualScreen()
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_JOY_POSITION, storeJoy)
        checkListFragmentInEmptyState()

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    @Test
    fun openDetailsFromListInDualLandscapeMode() {
        openDetailsFromListInDualMode()
    }

    @Test
    fun openDetailsFromListInDualPortraitMode() {
        setOrientationRight()
        openDetailsFromListInDualMode()
    }

    @Test
    fun spanDetailsFromList() {
        clickOnMapMarker(cityRedmond.name)
        clickOnListItemAtPosition(STORE_JOY_POSITION)

        switchFromSingleToDualScreen()
        checkMapFragment()
        checkDetailsFragment(storeJoy)

        navigateUp()
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_JOY_POSITION, storeJoy)
        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }
}
