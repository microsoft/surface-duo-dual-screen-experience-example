/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.product.customize

import androidx.test.rule.ActivityTestRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.dualscreen.testing.resetOrientation
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductColor
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductType
import com.microsoft.device.samples.dualscreenexperience.presentation.MainActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.product.PRODUCT_FIRST_POSITION
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkColorSelected
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeControl
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeImageLandscape
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeImageLandscapeContent
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeImagePortrait
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeImagePortraitContent
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkCustomizeShapes
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkShapeSelected
import com.microsoft.device.samples.dualscreenexperience.presentation.product.checkSingleModePlaceOrderButton
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
class ProductCustomizeSingleScreenTest {

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
    fun checkCustomizeInPortraitMode() {
        navigateToProductsSection()
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)
        clickOnCustomizeButton()

        checkSingleModePlaceOrderButton()
        checkCustomizeControl()
        checkCustomizeShapes()
        checkCustomizeImagePortrait()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImagePortraitContent(product.color, product.bodyShape)
    }

    @Test
    fun checkCustomizeInLandscapeMode() {
        device.setOrientationRight()

        navigateToProductsSection()
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)
        clickOnCustomizeButton()

        checkSingleModePlaceOrderButton()
        checkCustomizeControl()
        checkCustomizeShapes()
        checkCustomizeImageLandscape()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImageLandscapeContent(product.color, product.bodyShape)
    }

    @Test
    fun checkNewColorSelection() {
        navigateToProductsSection()
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)
        clickOnCustomizeButton()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImagePortraitContent(product.color, product.bodyShape)

        selectColor(ProductColor.AQUA)

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.AQUA)
        checkCustomizeImagePortraitContent(ProductColor.AQUA, product.bodyShape)

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
    }

    @Test
    fun checkNewShapeSelection() {
        navigateToProductsSection()
        clickOnListItemAtPosition(PRODUCT_FIRST_POSITION)
        clickOnCustomizeButton()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImagePortraitContent(product.color, product.bodyShape)

        selectShape(ProductType.ELECTRIC)

        checkShapeSelected(ProductType.ELECTRIC)
        checkColorSelected(ProductColor.LIGHT_GRAY)
        checkCustomizeImagePortraitContent(ProductColor.LIGHT_GRAY, ProductType.ELECTRIC)

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
    }
}
