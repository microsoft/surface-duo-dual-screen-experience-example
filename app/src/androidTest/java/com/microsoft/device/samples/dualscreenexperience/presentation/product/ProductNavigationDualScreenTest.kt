/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.product

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.dualscreen.testing.resetOrientation
import com.microsoft.device.dualscreen.testing.spanFromStart
import com.microsoft.device.samples.dualscreenexperience.presentation.MainActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.about.checkAboutInDualScreenMode
import com.microsoft.device.samples.dualscreenexperience.presentation.about.checkToolbarAbout
import com.microsoft.device.samples.dualscreenexperience.presentation.about.openAbout
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.checkToolbarDevItem
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.checkToolbarUserItem
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.openDevModeInDualMode
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.openUserMode
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class ProductNavigationDualScreenTest {

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
    fun openProductsInDualPortraitMode() {
        device.spanFromStart()

        navigateToProductsSection()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()
    }

    @Test
    fun openProductsInDualLandscapeMode() {
        device.spanFromStart()
        device.setOrientationRight()

        navigateToProductsSection()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()
    }

    @Test
    fun openAboutInDualPortraitMode() {
        device.spanFromStart()

        navigateToProductsSection()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()

        checkToolbarAbout()
        openAbout()
        checkAboutInDualScreenMode()

        goBack()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()

        checkToolbarAbout()
    }

    @Test
    fun openAboutInDualLandscapeMode() {
        device.spanFromStart()
        device.setOrientationRight()

        navigateToProductsSection()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()

        checkToolbarAbout()
        openAbout()
        checkAboutInDualScreenMode()

        goBack()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()

        checkToolbarAbout()
    }

    @Test
    fun openDevModeInDualPortraitMode() {
        device.spanFromStart()

        navigateToProductsSection()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()

        openDevModeInDualMode()
        checkToolbarUserItem()

        openUserMode()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()

        checkToolbarDevItem()
    }

    @Test
    fun openDevModeInDualLandscapeMode() {
        device.spanFromStart()
        device.setOrientationRight()

        navigateToProductsSection()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()

        openDevModeInDualMode()
        checkToolbarUserItem()

        openUserMode()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()

        checkToolbarDevItem()
    }

    @Test
    fun openCustomizeInDualPortraitMode() {
        device.spanFromStart()

        navigateToProductsSection()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()

        clickOnCustomizeButton()

        checkCustomizeControl()
        checkCustomizeImagePortrait()
        checkCustomizeDetails(product)
        checkCustomizeDetailsImagePortrait()

        goBack()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
    }

    @Test
    fun openCustomizeInDualLandscapeMode() {
        device.spanFromStart()
        device.setOrientationRight()

        navigateToProductsSection()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()

        clickOnCustomizeButton()

        checkCustomizeControl()
        checkCustomizeImageLandscape()
        checkCustomizeDetails(product)
        checkCustomizeDetailsImageLandscape()

        goBack()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
    }
}
