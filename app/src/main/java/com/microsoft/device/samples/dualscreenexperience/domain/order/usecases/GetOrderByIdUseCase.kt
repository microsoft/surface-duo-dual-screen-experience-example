/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.order.usecases

import com.microsoft.device.samples.dualscreenexperience.data.order.OrderDataSource
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import javax.inject.Inject

class GetOrderByIdUseCase @Inject constructor(private val orderRepository: OrderDataSource) {
    suspend fun get(orderId: Long): Order? = orderRepository.getById(orderId)?.let { Order(it) }
}
