/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.order.usecases

import com.microsoft.device.samples.dualscreenexperience.data.order.OrderDataSource
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderEntity
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import javax.inject.Inject

class AddItemToOrderUseCase @Inject constructor(private val orderRepository: OrderDataSource) {
    suspend fun addToOrder(item: OrderItem) {
        val openOrder = orderRepository.getAll().firstOrNull { !it.order.isSubmitted }
        val orderId = if (openOrder == null) {
            orderRepository.insert(
                OrderEntity(
                    orderTimestamp = System.currentTimeMillis(),
                    totalPrice = 0,
                    isSubmitted = false
                )
            )
        } else {
            openOrder.order.orderId
        }
        item.orderParentId = orderId
        orderRepository.insertItems(item.toEntity())
    }
}
