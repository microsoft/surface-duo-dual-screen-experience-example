/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product

import androidx.test.rule.ActivityTestRule
import com.microsoft.device.display.sampleheroapp.presentation.MainActivity
import com.microsoft.device.display.sampleheroapp.presentation.about.checkAboutInDualScreenMode
import com.microsoft.device.display.sampleheroapp.presentation.about.checkToolbarAbout
import com.microsoft.device.display.sampleheroapp.presentation.about.openAbout
import com.microsoft.device.display.sampleheroapp.presentation.devmode.checkToolbarDevItem
import com.microsoft.device.display.sampleheroapp.presentation.devmode.checkToolbarUserItem
import com.microsoft.device.display.sampleheroapp.presentation.devmode.openDevModeInDualMode
import com.microsoft.device.display.sampleheroapp.presentation.devmode.openUserMode
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

        navigateToProductsSection()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()
    }

    @Test
    fun openProductsInDualLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

        navigateToProductsSection()

        checkProductList(PRODUCT_FIRST_POSITION, product)
        checkProductDetails(product)
        checkCustomizeButton()
    }

    @Test
    fun openAboutInDualPortraitMode() {
        switchFromSingleToDualScreen()

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
        switchFromSingleToDualScreen()
        setOrientationRight()

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
        switchFromSingleToDualScreen()

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
        switchFromSingleToDualScreen()
        setOrientationRight()

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
        switchFromSingleToDualScreen()

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
        switchFromSingleToDualScreen()
        setOrientationRight()

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
