/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.order

import com.microsoft.device.display.sampleheroapp.data.order.local.OrderLocalDataSource
import com.microsoft.device.display.sampleheroapp.data.order.model.OrderEntity
import com.microsoft.device.display.sampleheroapp.data.order.model.OrderItemEntity
import com.microsoft.device.display.sampleheroapp.data.order.model.OrderWithItems
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val localDataSource: OrderLocalDataSource
) : OrderDataSource {

    override fun getOrderBySubmitted(submitted: Boolean) =
        localDataSource.getOrderBySubmitted(submitted)

    override suspend fun getAll(): List<OrderWithItems> = localDataSource.getAll()

    override suspend fun getById(orderId: Long): OrderWithItems? = localDataSource.getById(orderId)

    override suspend fun insert(order: OrderEntity) = localDataSource.insert(order)

    override suspend fun insertItems(vararg items: OrderItemEntity) {
        localDataSource.insertItems(*items)
    }

    override suspend fun updateItem(itemId: Long, newQuantity: Int) {
        localDataSource.updateItem(itemId, newQuantity)
    }

    override suspend fun deleteItem(itemId: Long) {
        localDataSource.deleteItem(itemId)
    }
}
