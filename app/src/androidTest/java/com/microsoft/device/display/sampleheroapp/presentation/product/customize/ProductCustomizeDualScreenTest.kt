/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.customize

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductColor
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductType
import com.microsoft.device.display.sampleheroapp.presentation.devmode.checkToolbarDevItem
import com.microsoft.device.display.sampleheroapp.presentation.devmode.checkToolbarUserItem
import com.microsoft.device.display.sampleheroapp.presentation.devmode.openDevModeInDualMode
import com.microsoft.device.display.sampleheroapp.presentation.devmode.openUserMode
import com.microsoft.device.display.sampleheroapp.presentation.product.checkColorSelected
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeControl
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeDetails
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeDetailsImageContent
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeDetailsImageLandscape
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeDetailsImagePortrait
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeImageLandscape
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeImageLandscapeContent
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeImagePortrait
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeImagePortraitContent
import com.microsoft.device.display.sampleheroapp.presentation.product.checkCustomizeShapes
import com.microsoft.device.display.sampleheroapp.presentation.product.checkShapeSelected
import com.microsoft.device.display.sampleheroapp.presentation.product.product
import com.microsoft.device.display.sampleheroapp.presentation.product.selectColor
import com.microsoft.device.display.sampleheroapp.presentation.product.selectShape
import com.microsoft.device.display.sampleheroapp.util.setOrientationNatural
import com.microsoft.device.display.sampleheroapp.util.setOrientationRight
import com.microsoft.device.display.sampleheroapp.util.switchFromDualToSingleScreen
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
class ProductCustomizeDualScreenTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    private val activityRule =
        object : ActivityTestRule<ProductCustomizeActivity>(ProductCustomizeActivity::class.java) {
            override fun getActivityIntent() =
                Intent(context, ProductCustomizeActivity::class.java).apply {
                    putExtra(ProductCustomizeViewModel.SELECTED_PRODUCT_ID, product.productId)
                }
        }

    @get:Rule
    var ruleChain: RuleChain =
        RuleChain.outerRule(HiltAndroidRule(this)).around(activityRule)

    @After
    fun resetOrientation() {
        unfreezeRotation()
        ScreenManagerProvider.getScreenManager().clear()
    }

    @Test
    fun checkCustomizeInPortraitMode() {
        switchFromSingleToDualScreen()

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
    fun checkCustomizeInLandscapeMode() {
        switchFromSingleToDualScreen()
        setOrientationRight()

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
        switchFromSingleToDualScreen()

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
        switchFromSingleToDualScreen()
        setOrientationRight()

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
    fun checkNewColorSelection() {
        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImagePortraitContent(product.color, product.bodyShape)

        selectColor(ProductColor.BLUE)

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.BLUE)
        checkCustomizeImagePortraitContent(ProductColor.BLUE, product.bodyShape)

        switchFromSingleToDualScreen()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.BLUE)
        checkCustomizeImagePortraitContent(ProductColor.BLUE, product.bodyShape)
        checkCustomizeDetailsImageContent(ProductColor.BLUE, product.bodyShape)

        selectColor(ProductColor.AQUA)

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.AQUA)
        checkCustomizeImagePortraitContent(ProductColor.AQUA, product.bodyShape)
        checkCustomizeDetailsImageContent(ProductColor.AQUA, product.bodyShape)

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
        checkCustomizeDetailsImageContent(ProductColor.WHITE, product.bodyShape)

        switchFromDualToSingleScreen()

        checkShapeSelected(product.bodyShape)
        checkColorSelected(ProductColor.WHITE)
        checkCustomizeImagePortraitContent(ProductColor.WHITE, product.bodyShape)
    }

    @Test
    fun checkNewShapeSelection() {
        checkShapeSelected(product.bodyShape)
        checkColorSelected(product.color)
        checkCustomizeImagePortraitContent(product.color, product.bodyShape)

        selectShape(ProductType.MUSICLANDER)

        checkShapeSelected(ProductType.MUSICLANDER)
        checkColorSelected(ProductColor.RED)
        checkCustomizeImagePortraitContent(ProductColor.RED, ProductType.MUSICLANDER)

        switchFromSingleToDualScreen()

        checkShapeSelected(ProductType.MUSICLANDER)
        checkColorSelected(ProductColor.RED)
        checkCustomizeImagePortraitContent(ProductColor.RED, ProductType.MUSICLANDER)
        checkCustomizeDetailsImageContent(ProductColor.RED, ProductType.MUSICLANDER)

        selectShape(ProductType.EXPLORER)

        checkShapeSelected(ProductType.EXPLORER)
        checkColorSelected(ProductColor.BLACK)
        checkCustomizeImagePortraitContent(ProductColor.BLACK, ProductType.EXPLORER)
        checkCustomizeDetailsImageContent(ProductColor.BLACK, ProductType.EXPLORER)

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
        checkCustomizeDetailsImageContent(ProductColor.DARK_RED, ProductType.WARLOCK)

        switchFromDualToSingleScreen()

        checkShapeSelected(ProductType.WARLOCK)
        checkColorSelected(ProductColor.DARK_RED)
        checkCustomizeImagePortraitContent(ProductColor.DARK_RED, ProductType.WARLOCK)
    }
}
