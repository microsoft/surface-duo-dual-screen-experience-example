/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.order

import com.microsoft.device.display.sampleheroapp.data.order.model.OrderEntity
import com.microsoft.device.display.sampleheroapp.data.order.model.OrderItemEntity
import com.microsoft.device.display.sampleheroapp.data.product.productEntity

val firstOrderItemEntity = OrderItemEntity(
    itemId = 1L,
    orderParentId = 1L,
    name = productEntity.name,
    price = productEntity.price,
    typeId = productEntity.typeId,
    colorId = productEntity.colorId,
    quantity = 1
)

val firstOrderEntity = OrderEntity(
    1L,
    1618832557,
    4354,
    false
)
