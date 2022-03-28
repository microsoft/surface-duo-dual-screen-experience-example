/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.product.customize

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.dualscreen.testing.resetOrientation
import com.microsoft.device.dualscreen.testing.spanFromStart
import com.microsoft.device.dualscreen.testing.unspanToStart
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductColor
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductType
import com.microsoft.device.samples.dualscreenexperience.presentation.MainActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.checkToolbarDevItem
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.checkToolbarUserItem
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.openDevModeInDualMode
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.openUserMode
import com.microsoft.device.samples.dualscreenexperience.presentation.product.PRODUCT_FIRST_POSITION
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkColorSelected
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeControl
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeDetails
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeDetailsImageContent
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeDetailsImageLandscape
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeDetailsImagePortrait
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeImageLandscape
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeImageLandscapeContent
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeImagePortrait
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeImagePortraitContent
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeShapes
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkShapeSelected
import com.microsoft.device.samples.dualscreenexperience.presentation.product.clickOnCustomizeButton
import com.microsoft.device.samples.dualscreenexperience.presentation.product.clickOnListItemAtPosition
import com.microsoft.device.samples.dualscreenexperience.presentation.product.navigateToProductsSection
import com.microsoft.device.samples.dualscreenexperience.presentation.product.product
import com.microsoft.device.samples.dualscreenexperience.presentation.product.selectColor
import com.microsoft.device.samples.dualscreenexperience.presentation.product.selectShape
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class ProductCustomizeDualScreenTest {

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
    fun checkCustomizeInDualPortraitMode() {
        device.spanFromStart()

        navigateToProductsSection()
        clickOnCustomizeButton()

        checkCustomizeControl()
        checkCustomizeShapes()
        checkCustomizeImagePortrait()

        checkCustomizeDetails(product)
        checkCustomizeDetailsImagePortrait()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImagePortraitContent(product.color, product.bodyShape)
        checkCustomizeDetailsImageContent(product.color, product.bodyShape)
    }

    @Test
    fun checkCustomizeInDualLandscapeMode() {
        device.spanFromStart()
        device.setOrientationRight()

        navigateToProductsSection()
        clickOnCustomizeButton()

        checkCustomizeControl()
        checkCustomizeShapes()
        checkCustomizeImageLandscape()

        checkCustomizeDetails(product)
        checkCustomizeDetailsImageLandscape()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImageLandscapeContent(product.color, product.bodyShape)
    }

    @Test
    fun openDevModeInDualPortraitMode() {
        device.spanFromStart()

        navigateToProductsSection()
        clickOnCustomizeButton()

        checkCustomizeControl()
        checkCustomizeShapes()
        checkCustomizeImagePortrait()

        checkCustomizeDetails(product)
        checkCustomizeDetailsImagePortrait()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImagePortraitContent(product.color, product.bodyShape)
        checkCustomizeDetailsImageContent(product.color, product.bodyShape)

        openDevModeInDualMode()
        checkToolbarUserItem()

        openUserMode()

        checkCustomizeControl()
        checkCustomizeShapes()
        checkCustomizeImagePortrait()

        checkCustomizeDetails(product)
        checkCustomizeDetailsImagePortrait()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImagePortraitContent(product.color, product.bodyShape)
        checkCustomizeDetailsImageContent(product.color, product.bodyShape)

        checkToolbarDevItem()
    }

    @Test
    fun openDevModeInDualLandscapeMode() {
        device.spanFromStart()
        device.setOrientationRight()

        navigateToProductsSection()
        clickOnCustomizeButton()

        checkCustomizeControl()
        checkCustomizeShapes()
        checkCustomizeImageLandscape()

        checkCustomizeDetails(product)
        checkCustomizeDetailsImageLandscape()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImageLandscapeContent(product.color, product.bodyShape)

        openDevModeInDualMode()
        checkToolbarUserItem()

        openUserMode()

        checkCustomizeControl()
        checkCustomizeShapes()
        checkCustomizeImageLandscape()

        checkCustomizeDetails(product)
        checkCustomizeDetailsImageLandscape()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImageLandscapeContent(product.color, product.bodyShape)

        checkToolbarDevItem()
    }

    @Test
    fun checkNewColorSelectionInDualMode() {
        navigateToProductsSection()
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)
        clickOnCustomizeButton()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImagePortraitContent(product.color, product.bodyShape)

        selectColor(ProductColor.BLUE)

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.BLUE)
        checkCustomizeImagePortraitContent(ProductColor.BLUE, product.bodyShape)

        device.spanFromStart()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.BLUE)
        checkCustomizeImagePortraitContent(ProductColor.BLUE, product.bodyShape)
        checkCustomizeDetailsImageContent(ProductColor.BLUE, product.bodyShape)

        selectColor(ProductColor.AQUA)

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.AQUA)
        checkCustomizeImagePortraitContent(ProductColor.AQUA, product.bodyShape)
        checkCustomizeDetailsImageContent(ProductColor.AQUA, product.bodyShape)

        device.setOrientationRight()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.AQUA)
        checkCustomizeImageLandscapeContent(ProductColor.AQUA, product.bodyShape)

        selectColor(ProductColor.WHITE)

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.WHITE)
        checkCustomizeImageLandscapeContent(ProductColor.WHITE, product.bodyShape)

        device.setOrientationNatural()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.WHITE)
        checkCustomizeImagePortraitContent(ProductColor.WHITE, product.bodyShape)
        checkCustomizeDetailsImageContent(ProductColor.WHITE, product.bodyShape)

        device.unspanToStart()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.WHITE)
        checkCustomizeImagePortraitContent(ProductColor.WHITE, product.bodyShape)
    }

    @Test
    fun checkNewShapeSelectionInDualMode() {
        navigateToProductsSection()
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)
        clickOnCustomizeButton()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImagePortraitContent(product.color, product.bodyShape)

        selectShape(ProductType.HARDROCK)

        checkShapeSelected(ProductType.HARDROCK)
        checkColorSelected(ProductColor.RED)
        checkCustomizeImagePortraitContent(ProductColor.RED, ProductType.HARDROCK)

        device.spanFromStart()

        checkShapeSelected(ProductType.HARDROCK)
        checkColorSelected(ProductColor.RED)
        checkCustomizeImagePortraitContent(ProductColor.RED, ProductType.HARDROCK)
        checkCustomizeDetailsImageContent(ProductColor.RED, ProductType.HARDROCK)

        selectShape(ProductType.ELECTRIC)

        checkShapeSelected(ProductType.ELECTRIC)
        checkColorSelected(ProductColor.LIGHT_GRAY)
        checkCustomizeImagePortraitContent(ProductColor.LIGHT_GRAY, ProductType.ELECTRIC)
        checkCustomizeDetailsImageContent(ProductColor.LIGHT_GRAY, ProductType.ELECTRIC)

        device.setOrientationRight()

        checkShapeSelected(ProductType.ELECTRIC)
        checkColorSelected(ProductColor.LIGHT_GRAY)
        checkCustomizeImageLandscapeContent(ProductColor.LIGHT_GRAY, ProductType.ELECTRIC)

        selectShape(ProductType.ROCK)

        checkShapeSelected(ProductType.ROCK)
        checkColorSelected(ProductColor.DARK_RED)
        checkCustomizeImageLandscapeContent(ProductColor.DARK_RED, ProductType.ROCK)

        device.setOrientationNatural()

        checkShapeSelected(ProductType.ROCK)
        checkColorSelected(ProductColor.DARK_RED)
        checkCustomizeImagePortraitContent(ProductColor.DARK_RED, ProductType.ROCK)
        checkCustomizeDetailsImageContent(ProductColor.DARK_RED, ProductType.ROCK)

        device.unspanToStart()

        checkShapeSelected(ProductType.ROCK)
        checkColorSelected(ProductColor.DARK_RED)
        checkCustomizeImagePortraitContent(ProductColor.DARK_RED, ProductType.ROCK)
    }
}
