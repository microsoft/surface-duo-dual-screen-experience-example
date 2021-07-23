/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.order.usecases

import com.microsoft.device.samples.dualscreenexperience.data.order.OrderDataSource
import javax.inject.Inject

class SubmitOrderUseCase @Inject constructor(private val orderRepository: OrderDataSource) {
    suspend fun submit() =
        orderRepository.getAll().firstOrNull { !it.order.isSubmitted }?.let { orderWithItems ->
            orderWithItems.order.totalPrice = orderWithItems.items.sumOf { it.price * it.quantity }
            orderWithItems.order.orderTimestamp = System.currentTimeMillis()
            orderWithItems.order.isSubmitted = true
            orderRepository.insert(orderWithItems.order)
        }
}
