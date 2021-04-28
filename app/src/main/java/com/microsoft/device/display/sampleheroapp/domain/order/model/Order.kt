/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.order.model

import com.microsoft.device.display.sampleheroapp.data.order.model.OrderWithItems

data class Order(
    val orderId: Long?,
    var orderTimestamp: Long,
    var totalPrice: Int,
    var isSubmitted: Boolean,
    var items: MutableList<OrderItem>
) {

    constructor(entity: OrderWithItems) :
        this(
            entity.order.orderId,
            entity.order.orderTimestamp,
            entity.order.totalPrice,
            entity.order.isSubmitted,
            entity.items.map { OrderItem(it) }.toMutableList()
        )
}
