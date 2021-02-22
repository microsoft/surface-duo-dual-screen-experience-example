/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.store.local

import com.microsoft.device.display.sampleheroapp.data.store.StoreDataSource
import com.microsoft.device.display.sampleheroapp.data.store.model.CityEntity
import com.microsoft.device.display.sampleheroapp.data.store.model.CityWithStoresEntity
import com.microsoft.device.display.sampleheroapp.data.store.model.StoreEntity
import javax.inject.Inject

class StoreLocalDataSource @Inject constructor(
    private val storeDao: StoreDao
) : StoreDataSource {

    override suspend fun getAll(): List<StoreEntity> = storeDao.getAll()

    override suspend fun getById(storeId: Long): StoreEntity? = storeDao.getById(storeId)

    override suspend fun getCitiesWithStores(): List<CityWithStoresEntity> =
        storeDao.getCityWithStores()

    override suspend fun insertAll(vararg stores: StoreEntity) {
        storeDao.insertAll(*stores)
    }

    override suspend fun insertAllCities(vararg cities: CityEntity) {
        storeDao.insertAllCities(*cities)
    }
}
