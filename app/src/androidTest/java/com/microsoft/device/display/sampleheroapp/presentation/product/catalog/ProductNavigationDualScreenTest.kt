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
import com.microsoft.device.display.sampleheroapp.presentation.product.openProductsTab
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
        openProductsTab()

        checkCatalogPageIsDisplayed(1)

        swipeViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(2)

        swipeViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(3)

        swipeViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(4)

        swipeViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(5)

        swipeViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(6)

        swipeViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(7)

        swipeViewPagerToTheLeft()
        checkCatalogPageIsDisplayed(8)

        Thread.sleep(1000)
    }

    @Test
    fun checkTabs() {
        openProductsTab()

        onView(
            allOf(
                withText(R.string.main_products_tab_products),
                isDescendantOfA(withId(R.id.catalog_tab_layout))
            )
        ).perform(click())

        onView(
            allOf(
                withText(R.string.main_products_tab_catalog),
                isDescendantOfA(withId(R.id.catalog_tab_layout))
            )
        ).perform(click())
    }

    @Test
    fun checkSwipeFromProductsTakesToCatalog() {
        openProductsTab()

        onView(
            allOf(
                withText(R.string.main_products_tab_products),
                isDescendantOfA(withId(R.id.catalog_tab_layout))
            )
        ).perform(click())

        swipeViewPagerToTheRight()
        checkCatalogPageIsDisplayed(1)
    }

    @Test
    fun checkAllCatalogItemsAfterRotation() {
        openProductsTab()
        setOrientationRight()

        checkAllCatalogItems()
        Thread.sleep(5000)
    }

    @Test
    fun checkTabsAfterRotation() {
        openProductsTab()
        setOrientationRight()

        checkTabs()
    }

    @Test
    fun checkSwipeFromProductsTakesToCatalogAfterRotate() {
        openProductsTab()
        setOrientationRight()

        checkSwipeFromProductsTakesToCatalog()
    }
}
