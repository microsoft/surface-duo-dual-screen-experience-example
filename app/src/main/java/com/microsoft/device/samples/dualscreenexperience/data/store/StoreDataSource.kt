/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.store

import com.microsoft.device.samples.dualscreenexperience.data.store.model.CityWithStoresEntity
import com.microsoft.device.samples.dualscreenexperience.data.store.model.StoreEntity

interface StoreDataSource {
    suspend fun getAll(): List<StoreEntity>
    suspend fun getById(storeId: Long): StoreEntity?
    suspend fun getCitiesWithStores(): List<CityWithStoresEntity>
}
