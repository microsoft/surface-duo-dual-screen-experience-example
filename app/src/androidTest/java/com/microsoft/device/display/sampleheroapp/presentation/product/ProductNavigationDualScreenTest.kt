/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product

import androidx.test.rule.ActivityTestRule
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
    fun openProductsInDualPortraitMode() {
        switchFromSingleToDualScreen()

        openProductsTab()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()
    }

    @Test
    fun openProductsInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        openProductsTab()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()
    }

    // The commented checks fail because of an SDK issue - see https://github.com/microsoft/surface-duo-hero-app-sample/issues/7
    @Test
    fun openCustomizeInDualPortraitMode() {
        switchFromSingleToDualScreen()

        openProductsTab()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()

        clickOnCustomizeButton()

        checkCustomizeControl()
        checkCustomizeImagePortrait()
        // checkCustomizeDetails(product)
        // checkCustomizeDetailsImagePortrait()

        goBack()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
    }

    // The commented checks fail because of an SDK issue - see https://github.com/microsoft/surface-duo-hero-app-sample/issues/7
    @Test
    fun openCustomizeInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        openProductsTab()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()

        clickOnCustomizeButton()

        checkCustomizeControl()
        checkCustomizeImageLandscape()
        // checkCustomizeDetails(product)
        // checkCustomizeDetailsImageLandscape()

        goBack()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
    }
}
