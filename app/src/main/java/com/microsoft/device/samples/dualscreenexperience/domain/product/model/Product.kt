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
    val deliveryDays: Int,
    var bodyShape: ProductType,
    var color: ProductColor,
    var guitarType: GuitarType
) {
    constructor(entity: ProductEntity) :
        this(
            entity.productId,
            entity.name,
            entity.price,
            entity.description,
            entity.rating,
            entity.fretsNumber,
            entity.deliveryDays,
            ProductType.get(entity.typeId),
            ProductColor.get(entity.colorId),
            GuitarType.get(entity.guitarTypeId)
        )
}

enum class ProductType(var bodyShapeId: Int, var colorList: List<ProductColor>) {
    ROCK(
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
            ProductColor.YELLOW,
            ProductColor.WHITE,
            ProductColor.ORANGE,
            ProductColor.AQUA
        )
    ),
    ELECTRIC(
        3,
        listOf(
            ProductColor.LIGHT_GRAY,
            ProductColor.MUSTARD,
            ProductColor.WHITE
        )
    ),
    HARDROCK(
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
    YELLOW(4),
    WHITE(5),
    ORANGE(6),
    AQUA(7),
    LIGHT_GRAY(8),
    MUSTARD(9),
    RED(10);

    companion object {
        fun get(colorId: Int?) = values().first { it.colorId == colorId }
    }
}

enum class GuitarType(var typeId: Int) {
    BASS(0),
    NORMAL(1);

    companion object {
        fun get(typeId: Int?) = values().first { it.typeId == typeId }
    }
}
