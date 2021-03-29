/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.util

import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductColor
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductType

fun getProductDrawable(color: ProductColor, type: ProductType): Int =
    when {
        type == ProductType.WARLOCK && color == ProductColor.DARK_RED -> R.drawable.red_warlock_guitar
        type == ProductType.WARLOCK && color == ProductColor.GRAY -> R.drawable.gray_warlock_guitar
        type == ProductType.CLASSIC && color == ProductColor.BLUE -> R.drawable.blue_classic_guitar
        type == ProductType.CLASSIC && color == ProductColor.WHITE -> R.drawable.white_classic_guitar
        type == ProductType.CLASSIC && color == ProductColor.ORANGE -> R.drawable.orange_classic_guitar
        type == ProductType.CLASSIC && color == ProductColor.AQUA -> R.drawable.aqua_classic_guitar
        type == ProductType.EXPLORER && color == ProductColor.BLACK -> R.drawable.black_explorer_guitar
        type == ProductType.EXPLORER && color == ProductColor.MUSTARD -> R.drawable.yellow_explorer_guitar
        type == ProductType.EXPLORER && color == ProductColor.WHITE -> R.drawable.white_explorer_guitar
        type == ProductType.MUSICLANDER && color == ProductColor.RED -> R.drawable.red_musiclander_guitar
        else -> R.drawable.orange_classic_guitar
    }

fun getColorRes(productColor: ProductColor): Int =
    when (productColor) {
        ProductColor.DARK_RED -> R.color.guitar_dark_red_color
        ProductColor.GRAY -> R.color.guitar_gray_color
        ProductColor.BLUE -> R.color.guitar_blue_color
        ProductColor.WHITE -> R.color.white
        ProductColor.ORANGE -> R.color.guitar_orange_color
        ProductColor.AQUA -> R.color.guitar_aqua_color
        ProductColor.BLACK -> R.color.black
        ProductColor.MUSTARD -> R.color.guitar_mustard_color
        ProductColor.RED -> R.color.guitar_red_color
    }

fun getTypeDrawable(productType: ProductType): Int =
    when (productType) {
        ProductType.WARLOCK -> R.drawable.warlock_shape
        ProductType.CLASSIC -> R.drawable.classic_shape
        ProductType.EXPLORER -> R.drawable.explorer_shape
        ProductType.MUSICLANDER -> R.drawable.musiclander_shape
    }
