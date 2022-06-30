/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.order.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.microsoft.device.samples.dualscreenexperience.data.order.OrderDataSource
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(private val orderRepository: OrderDataSource) {
    fun get(): LiveData<List<Order>> = Transformations.map(orderRepository.getAllSubmittedOrders()) { orderWithItems ->
        orderWithItems.map { Order(it) }
    }
}
