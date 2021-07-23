/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.order.model

import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderItemEntity
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
            entity.quantity
        )

    constructor(product: Product) :
        this(
            name = product.name,
            price = product.price,
            bodyShape = product.bodyShape,
            color = product.color,
            quantity = DEFAULT_QUANTITY
        )

    fun toEntity(): OrderItemEntity =
        OrderItemEntity(
            orderParentId = orderParentId!!,
            name = name,
            price = price,
            typeId = bodyShape.bodyShapeId,
            colorId = color.colorId,
            quantity = quantity
        )

    companion object {
        const val DEFAULT_QUANTITY = 1
    }
}
