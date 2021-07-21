/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.customize

import androidx.test.rule.ActivityTestRule
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductColor
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductType
import com.microsoft.device.display.sampleheroapp.presentation.MainActivity
import com.microsoft.device.display.sampleheroapp.presentation.product.PRODUCT_FIRST_POSITION
import com.microsoft.device.display.sampleheroapp.presentation.product.checkColorSelected
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeControl
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeImageLandscape
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeImageLandscapeContent
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeImagePortrait
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeImagePortraitContent
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeShapes
import com.microsoft.device.display.sampleheroapp.presentation.product.checkShapeSelected
import com.microsoft.device.display.sampleheroapp.presentation.product.checkSingleModePlaceOrderButton
import com.microsoft.device.display.sampleheroapp.presentation.product.clickOnCustomizeButton
import com.microsoft.device.display.sampleheroapp.presentation.product.clickOnListItemAtPosition
import com.microsoft.device.display.sampleheroapp.presentation.product.navigateToProductsSection
import com.microsoft.device.display.sampleheroapp.presentation.product.product
import com.microsoft.device.display.sampleheroapp.presentation.product.selectColor
import com.microsoft.device.display.sampleheroapp.presentation.product.selectShape
import com.microsoft.device.display.sampleheroapp.util.setOrientationNatural
import com.microsoft.device.display.sampleheroapp.util.setOrientationRight
import com.microsoft.device.display.sampleheroapp.util.unfreezeRotation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class ProductCustomizeSingleScreenTest {

    private val activityRule = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var ruleChain: RuleChain =
        RuleChain.outerRule(HiltAndroidRule(this)).around(activityRule)

    @After
    fun resetOrientation() {
        unfreezeRotation()
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
        setOrientationRight()

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

        setOrientationRight()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.AQUA)
        checkCustomizeImageLandscapeContent(ProductColor.AQUA, product.bodyShape)

        selectColor(ProductColor.WHITE)

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.WHITE)
        checkCustomizeImageLandscapeContent(ProductColor.WHITE, product.bodyShape)

        setOrientationNatural()

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

        selectShape(ProductType.EXPLORER)

        checkShapeSelected(ProductType.EXPLORER)
        checkColorSelected(ProductColor.BLACK)
        checkCustomizeImagePortraitContent(ProductColor.BLACK, ProductType.EXPLORER)

        setOrientationRight()

        checkShapeSelected(ProductType.EXPLORER)
        checkColorSelected(ProductColor.BLACK)
        checkCustomizeImageLandscapeContent(ProductColor.BLACK, ProductType.EXPLORER)

        selectShape(ProductType.WARLOCK)

        checkShapeSelected(ProductType.WARLOCK)
        checkColorSelected(ProductColor.DARK_RED)
        checkCustomizeImageLandscapeContent(ProductColor.DARK_RED, ProductType.WARLOCK)

        setOrientationNatural()

        checkShapeSelected(ProductType.WARLOCK)
        checkColorSelected(ProductColor.DARK_RED)
        checkCustomizeImagePortraitContent(ProductColor.DARK_RED, ProductType.WARLOCK)
    }
}
