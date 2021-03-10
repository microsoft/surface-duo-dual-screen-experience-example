/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.store

import com.microsoft.device.display.sampleheroapp.data.store.local.StoreLocalDataSource
import com.microsoft.device.display.sampleheroapp.data.store.model.CityEntity
import com.microsoft.device.display.sampleheroapp.data.store.model.CityWithStoresEntity
import com.microsoft.device.display.sampleheroapp.data.store.model.StoreEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRepository @Inject constructor(
    private val localDataSource: StoreLocalDataSource
) : StoreDataSource {

    override suspend fun getAll(): List<StoreEntity> = localDataSource.getAll()

    override suspend fun getById(storeId: Long): StoreEntity? = localDataSource.getById(storeId)

    override suspend fun getCitiesWithStores(): List<CityWithStoresEntity> =
        localDataSource.getCitiesWithStores()

    override suspend fun insert(vararg stores: StoreEntity) {
        localDataSource.insert(*stores)
    }

    override suspend fun insertCities(vararg cities: CityEntity) {
        localDataSource.insertCities(*cities)
    }
}
