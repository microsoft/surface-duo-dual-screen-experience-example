/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.dualscreen.testing.resetOrientation
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.presentation.MainActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.store.checkToolbar
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class CatalogNavigationDualScreenTest {

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
    fun checkAllCatalogItems() {
        navigateToCatalogSection()

        checkToolbar(R.string.toolbar_catalog_title)

        checkCatalogPageIsDisplayed(1)

        swipeCatalogViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(2)

        swipeCatalogViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(3)

        swipeCatalogViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(4)

        swipeCatalogViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(5)

        swipeCatalogViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(6)

        swipeCatalogViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(7)

        checkToolbar(R.string.toolbar_catalog_title)
    }

    @Test
    fun checkAllCatalogItemsAfterRotation() {
        navigateToCatalogSection()
        device.setOrientationRight()

        checkAllCatalogItems()
    }
}
