/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.order.model

import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderItemEntity
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.GuitarType
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.Product
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductColor
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductType

data class OrderItem(
    val itemId: Long? = null,
    var orderParentId: Long? = null,
    val name: String,
    val price: Int,
    val bodyShape: ProductType,
    val color: ProductColor,
    val guitarType: GuitarType,
    var quantity: Int,
) {
    constructor(entity: OrderItemEntity) :
        this(
            entity.itemId,
            entity.orderParentId,
            entity.name,
            entity.price,
            ProductType.get(entity.typeId),
            ProductColor.get(entity.colorId),
            GuitarType.get(entity.guitarTypeId),
            entity.quantity
        )

    constructor(product: Product) :
        this(
            name = product.name,
            price = product.price,
            bodyShape = product.bodyShape,
            color = product.color,
            guitarType = product.guitarType,
            quantity = DEFAULT_QUANTITY
        )

    fun toEntity(): OrderItemEntity =
        OrderItemEntity(
            orderParentId = orderParentId!!,
            name = name,
            price = price,
            typeId = bodyShape.bodyShapeId,
            colorId = color.colorId,
            guitarTypeId = guitarType.typeId,
            quantity = quantity
        )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OrderItem

        if (orderParentId != other.orderParentId) return false
        if (name != other.name) return false
        if (price != other.price) return false
        if (bodyShape != other.bodyShape) return false
        if (color != other.color) return false
        if (guitarType != other.guitarType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = orderParentId?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + price
        result = 31 * result + bodyShape.hashCode()
        result = 31 * result + color.hashCode()
        result = 31 * result + guitarType.hashCode()
        return result
    }

    companion object {
        const val DEFAULT_QUANTITY = 1
    }
}
