/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.product

import androidx.test.rule.ActivityTestRule
import com.microsoft.device.samples.dualscreenexperience.presentation.MainActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.about.checkAboutInSingleScreenMode
import com.microsoft.device.samples.dualscreenexperience.presentation.about.checkToolbarAbout
import com.microsoft.device.samples.dualscreenexperience.presentation.about.openAbout
import com.microsoft.device.samples.dualscreenexperience.util.setOrientationRight
import com.microsoft.device.samples.dualscreenexperience.util.unfreezeRotation
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
        checkProductList(PRODUCT_FIRST_POSITION, product)
    }

    @Test
    fun openProductsInLandscapeMode() {
        setOrientationRight()

        navigateToProductsSection()
        checkProductList(PRODUCT_FIRST_POSITION, product)
    }

    @Test
    fun openAboutInPortraitMode() {
        navigateToProductsSection()

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
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)

        checkProductDetails(product)
        checkCustomizeButton()
    }

    @Test
    fun openDetailsInLandscapeMode() {
        setOrientationRight()

        navigateToProductsSection()
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)

        checkProductDetails(product)
        checkCustomizeButton()
    }

    @Test
    fun openCustomizeInPortraitMode() {
        navigateToProductsSection()
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
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)

        checkCustomizeButton()
        clickOnCustomizeButton()

        checkCustomizeControl()
        checkCustomizeImageLandscape()

        goBack()

        checkProductDetails(product)
    }
}
