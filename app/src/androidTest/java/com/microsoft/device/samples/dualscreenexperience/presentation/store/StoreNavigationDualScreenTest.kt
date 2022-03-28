/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.dualscreen.testing.resetOrientation
import com.microsoft.device.dualscreen.testing.spanFromStart
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.presentation.MainActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.about.checkAboutInDualScreenMode
import com.microsoft.device.samples.dualscreenexperience.presentation.about.checkToolbarAbout
import com.microsoft.device.samples.dualscreenexperience.presentation.about.openAbout
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.checkToolbarDevItem
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.checkToolbarUserItem
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.navigateUp
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.openDevModeInDualMode
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.openUserMode
import com.microsoft.device.samples.dualscreenexperience.presentation.launch.goBack
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class StoreNavigationDualScreenTest : BaseStoreNavigationTest() {

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
    fun openMapInDualLandscapeMode() {
        device.spanFromStart()

        openMapInSingleMode()
    }

    @Test
    fun openMapInDualPortraitMode() {
        device.spanFromStart()
        device.setOrientationRight()

        openMapInSingleMode()

        checkToolbarDevItem()
    }

    @Test
    fun openAboutInDualPortraitMode() {
        device.spanFromStart()

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
        device.spanFromStart()
        device.setOrientationRight()

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
        device.spanFromStart()

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
        device.spanFromStart()
        device.setOrientationRight()

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
        device.setOrientationRight()
        openDetailsFromMapInDualMode()

        checkToolbarDevItem()
    }

    @Test
    fun openListFromDetailsInDualLandscapeMode() {
        openListFromDetailsInDualMode()

        checkToolbarDevItem()
    }

    @Test
    fun openListFromDetailsInDualPortraitMode() {
        device.setOrientationRight()
        openListFromDetailsInDualMode()

        checkToolbarDevItem()
    }

    @Test
    fun spanDetailsFromMap() {
        clickOnMapMarker(storeWithoutCity.name)

        device.spanFromStart()
        checkMapFragment()
        checkDetailsFragment(storeWithoutCity)

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
        device.setOrientationRight()
        openListFromMapInDualMode()

        checkToolbarDevItem()
    }

    @Test
    fun spanListFromMap() {
        clickOnMapMarker(cityRedmond.name)

        device.spanFromStart()
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_FIRST_POSITION, firstStore)
        checkListFragmentInEmptyState(device)

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
        device.setOrientationRight()
        openDetailsFromListInDualMode()

        checkToolbarDevItem()
    }

    @Test
    fun spanDetailsFromList() {
        clickOnMapMarker(cityRedmond.name)
        clickOnListItemAtPosition(STORE_FIRST_POSITION)

        device.spanFromStart()
        checkMapFragment()
        checkDetailsFragment(firstStore)

        navigateUp()
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_FIRST_POSITION, firstStore)
        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
        checkToolbarDevItem()
    }
}
