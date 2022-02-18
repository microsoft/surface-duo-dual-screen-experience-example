/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog

import androidx.test.rule.ActivityTestRule
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.presentation.MainActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.store.checkToolbar
import com.microsoft.device.samples.dualscreenexperience.util.setOrientationRight
import com.microsoft.device.samples.dualscreenexperience.util.unfreezeRotation
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
        setOrientationRight()

        checkAllCatalogItems()
    }
}
