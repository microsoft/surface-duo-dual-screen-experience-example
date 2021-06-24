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
import com.microsoft.device.display.sampleheroapp.presentation.about.checkAboutInDualScreenMode
import com.microsoft.device.display.sampleheroapp.presentation.about.checkToolbarAbout
import com.microsoft.device.display.sampleheroapp.presentation.about.openAbout
import com.microsoft.device.display.sampleheroapp.presentation.devmode.checkToolbarDevItem
import com.microsoft.device.display.sampleheroapp.presentation.devmode.checkToolbarUserItem
import com.microsoft.device.display.sampleheroapp.presentation.devmode.navigateUp
import com.microsoft.device.display.sampleheroapp.presentation.devmode.openDevModeInDualMode
import com.microsoft.device.display.sampleheroapp.presentation.devmode.openUserMode
import com.microsoft.device.display.sampleheroapp.presentation.launch.goBack
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
class StoreNavigationDualScreenTest : BaseStoreNavigationTest() {

    private val activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var ruleChain: RuleChain =
        RuleChain.outerRule(HiltAndroidRule(this)).around(activityRule)

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

        checkToolbarDevItem()
    }

    @Test
    fun openAboutInDualPortraitMode() {
        switchFromSingleToDualScreen()

        openMapInSingleMode()

        checkToolbarAbout()
        openAbout()
        checkAboutInDualScreenMode()

        goBack()

        checkMapFragment()
        checkToolbar(R.string.app_name)
        checkToolbarAbout()
    }

    @Test
    fun openAboutInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        openMapInSingleMode()

        checkToolbarAbout()
        openAbout()
        checkAboutInDualScreenMode()

        goBack()

        checkMapFragment()
        checkToolbar(R.string.app_name)
        checkToolbarAbout()
    }

    @Test
    fun openDevModeInDualPortraitMode() {
        switchFromSingleToDualScreen()

        openMapInSingleMode()

        openDevModeInDualMode()
        checkToolbarUserItem()

        openUserMode()

        checkMapFragment()
        checkToolbar(R.string.app_name)
        checkToolbarDevItem()
    }

    @Test
    fun openDevModeInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        openMapInSingleMode()

        openDevModeInDualMode()
        checkToolbarUserItem()

        openUserMode()

        checkMapFragment()
        checkToolbar(R.string.app_name)
        checkToolbarDevItem()
    }

    @Test
    fun openDetailsFromMapInDualLandscapeMode() {
        openDetailsFromMapInDualMode()

        checkToolbarDevItem()
    }

    @Test
    fun openDetailsFromMapInDualPortraitMode() {
        setOrientationRight()
        openDetailsFromMapInDualMode()

        checkToolbarDevItem()
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
        checkToolbarDevItem()
    }

    @Test
    fun openListFromMapInDualLandscapeMode() {
        openListFromMapInDualMode()

        checkToolbarDevItem()
    }

    @Test
    fun openListFromMapInDualPortraitMode() {
        setOrientationRight()
        openListFromMapInDualMode()

        checkToolbarDevItem()
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
        checkToolbarDevItem()
    }

    @Test
    fun openDetailsFromListInDualLandscapeMode() {
        openDetailsFromListInDualMode()

        checkToolbarDevItem()
    }

    @Test
    fun openDetailsFromListInDualPortraitMode() {
        setOrientationRight()
        openDetailsFromListInDualMode()

        checkToolbarDevItem()
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
        checkToolbarDevItem()
    }
}
