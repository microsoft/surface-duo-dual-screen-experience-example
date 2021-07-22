/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.order.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderEntity
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderItemEntity
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderWithItems

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(vararg items: OrderItemEntity)

    @Query("UPDATE items SET quantity = :newQuantity WHERE itemId = :itemId")
    suspend fun updateItem(itemId: Long, newQuantity: Int)

    @Query("DELETE FROM items WHERE itemId = :itemId")
    suspend fun deleteItem(itemId: Long)

    @Transaction
    @Query("SELECT * FROM orders")
    suspend fun getAll(): List<OrderWithItems>

    @Transaction
    @Query("SELECT * FROM orders where orderId = :orderId")
    suspend fun getById(orderId: Long): OrderWithItems?

    @Transaction
    @Query("SELECT * FROM orders where isSubmitted == :submitted LIMIT 1")
    fun getOrderBySubmitted(submitted: Boolean): LiveData<OrderWithItems?>
}
