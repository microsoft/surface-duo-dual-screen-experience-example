/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.microsoft.device.samples.dualscreenexperience.data.order.local.OrderDao
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderEntity
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderItemEntity
import com.microsoft.device.samples.dualscreenexperience.data.product.local.ProductDao
import com.microsoft.device.samples.dualscreenexperience.data.product.model.ProductEntity
import com.microsoft.device.samples.dualscreenexperience.data.store.local.StoreDao
import com.microsoft.device.samples.dualscreenexperience.data.store.model.CityEntity
import com.microsoft.device.samples.dualscreenexperience.data.store.model.StoreEntity

@Database(
    entities = [
        ProductEntity::class,
        StoreEntity::class,
        CityEntity::class,
        OrderEntity::class,
        OrderItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun storeDao(): StoreDao
    abstract fun orderDao(): OrderDao
}
