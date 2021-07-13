/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.catalog

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.presentation.MainActivity
import com.microsoft.device.display.sampleheroapp.presentation.product.navigateToProductsSection
import com.microsoft.device.display.sampleheroapp.util.setOrientationRight
import com.microsoft.device.display.sampleheroapp.util.unfreezeRotation
import com.microsoft.device.dualscreen.ScreenManagerProvider
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class ProductNavigationDualScreenTest {

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
        navigateToProductsSection()

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
    }

    @Test
    fun checkTabs() {
        navigateToProductsSection()

        openProductsTab()
        openCatalogTab()
    }

    @Test
    fun checkSwipeFromProductsTakesToCatalog() {
        navigateToProductsSection()

        onView(
            allOf(
                withText(R.string.main_products_tab_products),
                isDescendantOfA(withId(R.id.catalog_tab_layout))
            )
        ).perform(click())

        swipeHostViewPagerToTheRight()
        checkCatalogPageIsDisplayed(1)
    }

    @Test
    fun checkAllCatalogItemsAfterRotation() {
        navigateToProductsSection()
        setOrientationRight()

        checkAllCatalogItems()
    }

    @Test
    fun checkTabsAfterRotation() {
        navigateToProductsSection()
        setOrientationRight()

        checkTabs()
    }

    @Test
    fun checkSwipeFromProductsTakesToCatalogAfterRotate() {
        navigateToProductsSection()
        setOrientationRight()

        checkSwipeFromProductsTakesToCatalog()
    }
}
