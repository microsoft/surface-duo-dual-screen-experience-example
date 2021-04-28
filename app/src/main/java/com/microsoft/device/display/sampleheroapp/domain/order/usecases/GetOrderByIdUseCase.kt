/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.order.usecases

import com.microsoft.device.display.sampleheroapp.data.order.OrderDataSource
import com.microsoft.device.display.sampleheroapp.domain.order.model.Order
import javax.inject.Inject

class GetOrderByIdUseCase @Inject constructor(private val orderRepository: OrderDataSource) {
    suspend fun get(orderId: Long): Order? = orderRepository.getById(orderId)?.let { Order(it) }
}
