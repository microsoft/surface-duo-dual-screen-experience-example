/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.store

import com.microsoft.device.display.sampleheroapp.data.store.model.CityEntity
import com.microsoft.device.display.sampleheroapp.data.store.model.CityWithStoresEntity
import com.microsoft.device.display.sampleheroapp.data.store.model.StoreEntity

interface StoreDataSource {
    suspend fun getAll(): List<StoreEntity>
    suspend fun getById(storeId: Long): StoreEntity?
    suspend fun getCitiesWithStores(): List<CityWithStoresEntity>
    suspend fun insertAll(vararg stores: StoreEntity)
    suspend fun insertAllCities(vararg cities: CityEntity)
}
