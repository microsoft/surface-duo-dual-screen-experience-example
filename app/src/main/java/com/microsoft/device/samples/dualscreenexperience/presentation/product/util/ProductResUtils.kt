/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.product.util

import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductColor
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductType

fun getProductDrawable(color: ProductColor, type: ProductType): Int =
    when {
        type == ProductType.ROCK && color == ProductColor.DARK_RED -> R.drawable.red_rock_guitar_normal
        type == ProductType.ROCK && color == ProductColor.GRAY -> R.drawable.gray_rock_guitar_normal
        type == ProductType.CLASSIC && color == ProductColor.BLUE -> R.drawable.blue_classic_guitar_normal
        type == ProductType.CLASSIC && color == ProductColor.YELLOW -> R.drawable.yellow_classic_guitar_normal
        type == ProductType.CLASSIC && color == ProductColor.WHITE -> R.drawable.white_classic_guitar_normal
        type == ProductType.CLASSIC && color == ProductColor.ORANGE -> R.drawable.orange_classic_guitar_normal
        type == ProductType.CLASSIC && color == ProductColor.AQUA -> R.drawable.aqua_classic_guitar_normal
        type == ProductType.ELECTRIC && color == ProductColor.LIGHT_GRAY -> R.drawable.light_gray_electric_guitar_normal
        type == ProductType.ELECTRIC && color == ProductColor.MUSTARD -> R.drawable.yellow_electric_guitar_normal
        type == ProductType.ELECTRIC && color == ProductColor.WHITE -> R.drawable.white_electric_guitar_normal
        type == ProductType.HARDROCK && color == ProductColor.RED -> R.drawable.red_hardrock_guitar_normal
        else -> R.drawable.orange_classic_guitar_normal
    }

fun getProductContentDescription(color: ProductColor, type: ProductType): Int =
    when {
        type == ProductType.ROCK && color == ProductColor.DARK_RED -> R.string.product_rock_dark_red_normal
        type == ProductType.ROCK && color == ProductColor.GRAY -> R.string.product_rock_gray_normal
        type == ProductType.CLASSIC && color == ProductColor.BLUE -> R.string.product_classic_blue_normal
        type == ProductType.CLASSIC && color == ProductColor.YELLOW -> R.string.product_classic_yellow_normal
        type == ProductType.CLASSIC && color == ProductColor.WHITE -> R.string.product_classic_white_normal
        type == ProductType.CLASSIC && color == ProductColor.ORANGE -> R.string.product_classic_orange_normal
        type == ProductType.CLASSIC && color == ProductColor.AQUA -> R.string.product_classic_aqua_normal
        type == ProductType.ELECTRIC && color == ProductColor.LIGHT_GRAY -> R.string.product_electric_light_gray_normal
        type == ProductType.ELECTRIC && color == ProductColor.MUSTARD -> R.string.product_electric_mustard_normal
        type == ProductType.ELECTRIC && color == ProductColor.WHITE -> R.string.product_electric_white_normal
        type == ProductType.HARDROCK && color == ProductColor.RED -> R.string.product_hardrock_red_normal
        else -> R.string.product_classic_orange_normal
    }

fun getColorRes(productColor: ProductColor): Int =
    when (productColor) {
        ProductColor.DARK_RED -> R.color.guitar_dark_red_color
        ProductColor.GRAY -> R.color.guitar_gray_color
        ProductColor.BLUE -> R.color.guitar_blue_color
        ProductColor.YELLOW -> R.color.guitar_yellow_color
        ProductColor.WHITE -> R.color.guitar_white_color
        ProductColor.ORANGE -> R.color.guitar_orange_color
        ProductColor.AQUA -> R.color.guitar_aqua_color
        ProductColor.LIGHT_GRAY -> R.color.guitar_light_gray_color
        ProductColor.MUSTARD -> R.color.guitar_mustard_color
        ProductColor.RED -> R.color.guitar_red_color
    }

fun getTypeDrawable(productType: ProductType): Int =
    when (productType) {
        ProductType.ROCK -> R.drawable.rock_shape
        ProductType.CLASSIC -> R.drawable.classic_shape
        ProductType.ELECTRIC -> R.drawable.electric_shape
        ProductType.HARDROCK -> R.drawable.hardrock_shape
    }
