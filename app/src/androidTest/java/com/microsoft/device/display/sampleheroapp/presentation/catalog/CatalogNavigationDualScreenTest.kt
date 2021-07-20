/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.catalog

import androidx.test.rule.ActivityTestRule
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.presentation.MainActivity
import com.microsoft.device.display.sampleheroapp.presentation.store.checkToolbar
import com.microsoft.device.display.sampleheroapp.util.setOrientationRight
import com.microsoft.device.display.sampleheroapp.util.unfreezeRotation
import com.microsoft.device.dualscreen.ScreenManagerProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class CatalogNavigationDualScreenTest {

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
    fun checkAllCatalogItems() {
        navigateToCatalogSection()

        checkToolbar(R.string.nav_catalog_title)

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

        swipeCatalogViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(8)

        checkToolbar(R.string.nav_catalog_title)
    }

    @Test
    fun checkAllCatalogItemsAfterRotation() {
        navigateToCatalogSection()
        setOrientationRight()

        checkAllCatalogItems()
    }
}
