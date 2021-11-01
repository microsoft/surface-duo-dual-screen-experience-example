/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.order.local

import com.microsoft.device.samples.dualscreenexperience.data.order.OrderDataSource
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderEntity
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderItemEntity
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderWithItems
import javax.inject.Inject

class OrderLocalDataSource @Inject constructor(
    private val orderDao: OrderDao
) : OrderDataSource {

    override fun getOrderBySubmitted(submitted: Boolean) = orderDao.getOrderBySubmitted(submitted)

    override suspend fun getAll(): List<OrderWithItems> = orderDao.getAll()

    override suspend fun getById(orderId: Long) = orderDao.getById(orderId)

    override suspend fun insert(order: OrderEntity) = orderDao.insert(order)

    override suspend fun insertItems(vararg items: OrderItemEntity) {
        orderDao.insertItems(*items)
    }

    override suspend fun updateItem(itemId: Long, newQuantity: Int) {
        orderDao.updateItem(itemId, newQuantity)
    }

    override suspend fun deleteItem(itemId: Long) {
        orderDao.deleteItem(itemId)
    }
}
