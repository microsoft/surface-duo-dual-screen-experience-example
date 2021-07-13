/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product

import androidx.test.rule.ActivityTestRule
import com.microsoft.device.display.sampleheroapp.presentation.MainActivity
import com.microsoft.device.display.sampleheroapp.presentation.about.checkAboutInSingleScreenMode
import com.microsoft.device.display.sampleheroapp.presentation.about.checkToolbarAbout
import com.microsoft.device.display.sampleheroapp.presentation.about.openAbout
import com.microsoft.device.display.sampleheroapp.presentation.product.catalog.openProductsTab
import com.microsoft.device.display.sampleheroapp.util.setOrientationRight
import com.microsoft.device.display.sampleheroapp.util.unfreezeRotation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class ProductNavigationSingleScreenTest {
    private val activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var ruleChain: RuleChain =
        RuleChain.outerRule(HiltAndroidRule(this)).around(activityRule)

    @After
    fun resetOrientation() {
        unfreezeRotation()
    }

    @Test
    fun openProductsInPortraitMode() {
        navigateToProductsSection()
        openProductsTab()
        checkProductList(PRODUCT_FIRST_POSITION, product)
    }

    @Test
    fun openProductsInLandscapeMode() {
        setOrientationRight()

        navigateToProductsSection()
        openProductsTab()
        checkProductList(PRODUCT_FIRST_POSITION, product)
    }

    @Test
    fun openAboutInPortraitMode() {
        navigateToProductsSection()
        openProductsTab()

        checkProductList(PRODUCT_FIRST_POSITION, product)

        checkToolbarAbout()
        openAbout()
        checkAboutInSingleScreenMode()

        goBack()

        checkProductList(PRODUCT_FIRST_POSITION, product)

        checkToolbarAbout()
    }

    @Test
    fun openAboutInLandscapeMode() {
        setOrientationRight()

        navigateToProductsSection()
        openProductsTab()

        checkProductList(PRODUCT_FIRST_POSITION, product)

        checkToolbarAbout()
        openAbout()
        checkAboutInSingleScreenMode()

        goBack()

        checkProductList(PRODUCT_FIRST_POSITION, product)

        checkToolbarAbout()
    }

    @Test
    fun openDetailsInPortraitMode() {
        navigateToProductsSection()
        openProductsTab()
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)

        checkProductDetails(product)
        checkCustomizeButton()
    }

    @Test
    fun openDetailsInLandscapeMode() {
        setOrientationRight()

        navigateToProductsSection()
        openProductsTab()
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)

        checkProductDetails(product)
        checkCustomizeButton()
    }

    @Test
    fun openCustomizeInPortraitMode() {
        navigateToProductsSection()
        openProductsTab()
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)

        checkCustomizeButton()
        clickOnCustomizeButton()

        checkCustomizeControl()
        checkCustomizeImagePortrait()

        goBack()

        checkProductDetails(product)
    }

    @Test
    fun openCustomizeInLandscapeMode() {
        setOrientationRight()

        navigateToProductsSection()
        openProductsTab()
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)

        checkCustomizeButton()
        clickOnCustomizeButton()

        checkCustomizeControl()
        checkCustomizeImageLandscape()

        goBack()

        checkProductDetails(product)
    }
}
