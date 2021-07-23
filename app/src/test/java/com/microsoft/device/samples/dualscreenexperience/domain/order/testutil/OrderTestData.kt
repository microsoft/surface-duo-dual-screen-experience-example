/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.order.testutil

import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderEntity
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderItemEntity
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderWithItems
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.product
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.productEntity

val firstOrderItem = OrderItem(
    orderParentId = 1L,
    name = product.name,
    price = product.price,
    bodyShape = product.bodyShape,
    color = product.color,
    quantity = 1
)

val firstOrderItemEntity = OrderItemEntity(
    orderParentId = 1L,
    name = productEntity.name,
    price = productEntity.price,
    typeId = productEntity.typeId,
    colorId = productEntity.colorId,
    quantity = 1
)

val firstOrder = Order(
    1L,
    1618832557,
    4354,
    false,
    mutableListOf()
)

val firstOrderEntity = OrderEntity(
    1L,
    1618832557,
    4354,
    false
)

val orderWithItems = OrderWithItems(
    firstOrderEntity,
    mutableListOf(firstOrderItemEntity)
)
