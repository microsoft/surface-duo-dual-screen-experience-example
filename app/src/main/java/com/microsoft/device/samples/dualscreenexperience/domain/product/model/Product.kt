/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.product.model

import com.microsoft.device.samples.dualscreenexperience.data.product.model.ProductEntity

data class Product(
    val productId: Long,
    val name: String,
    val price: Int,
    val description: String,
    val rating: Float,
    val fretsNumber: Int,
    var bodyShape: ProductType,
    var color: ProductColor
) {
    constructor(entity: ProductEntity) :
        this(
            entity.productId,
            entity.name,
            entity.price,
            entity.description,
            entity.rating,
            entity.fretsNumber,
            ProductType.get(entity.typeId),
            ProductColor.get(entity.colorId)
        )
}

enum class ProductType(var bodyShapeId: Int, var colorList: List<ProductColor>) {
    WARLOCK(
        1,
        listOf(
            ProductColor.DARK_RED,
            ProductColor.GRAY
        )
    ),
    CLASSIC(
        2,
        listOf(
            ProductColor.BLUE,
            ProductColor.WHITE,
            ProductColor.ORANGE,
            ProductColor.AQUA
        )
    ),
    EXPLORER(
        3,
        listOf(
            ProductColor.BLACK,
            ProductColor.MUSTARD,
            ProductColor.WHITE
        )
    ),
    MUSICLANDER(
        4,
        listOf(
            ProductColor.RED
        )
    );

    companion object {
        fun get(bodyShapeId: Int?) = values().first { it.bodyShapeId == bodyShapeId }
    }
}

enum class ProductColor(var colorId: Int) {
    DARK_RED(1),
    GRAY(2),
    BLUE(3),
    WHITE(4),
    ORANGE(5),
    AQUA(6),
    BLACK(7),
    MUSTARD(8),
    RED(9);

    companion object {
        fun get(colorId: Int?) = values().first { it.colorId == colorId }
    }
}
