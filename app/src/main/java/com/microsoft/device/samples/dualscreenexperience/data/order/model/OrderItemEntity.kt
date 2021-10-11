/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.order.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true) var itemId: Long? = null,
    val orderParentId: Long,
    val name: String,
    val price: Int,
    val typeId: Int,
    val colorId: Int,
    val guitarTypeId: Int,
    var quantity: Int
)
