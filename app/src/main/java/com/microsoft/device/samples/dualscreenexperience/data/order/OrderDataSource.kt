/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.order

import androidx.lifecycle.LiveData
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderEntity
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderItemEntity
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderWithItems

interface OrderDataSource {
    fun getOrderBySubmitted(submitted: Boolean): LiveData<OrderWithItems?>

    suspend fun getAll(): List<OrderWithItems>
    suspend fun getById(orderId: Long): OrderWithItems?
    suspend fun insert(order: OrderEntity): Long
    suspend fun insertItems(vararg items: OrderItemEntity)
    suspend fun updateItem(itemId: Long, newQuantity: Int)
    suspend fun deleteItem(itemId: Long)
}
