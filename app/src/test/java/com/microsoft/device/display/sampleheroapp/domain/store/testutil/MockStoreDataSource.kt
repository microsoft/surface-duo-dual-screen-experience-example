/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.store.testutil

import com.microsoft.device.display.sampleheroapp.data.store.StoreDataSource
import com.microsoft.device.display.sampleheroapp.data.store.model.CityEntity
import com.microsoft.device.display.sampleheroapp.data.store.model.CityWithStoresEntity
import com.microsoft.device.display.sampleheroapp.data.store.model.StoreEntity

class MockStoreDataSource : StoreDataSource {
    private var storeEntityMap = mutableMapOf<Long, StoreEntity>()
    private var cityEntityMap = mutableMapOf<Long, CityEntity>()
    private var cityWithStoreEntityMap = mutableMapOf<Long, CityWithStoresEntity>()

    override suspend fun getAll(): List<StoreEntity> = storeEntityMap.values.toList()

    override suspend fun getById(storeId: Long): StoreEntity? = storeEntityMap[storeId]

    override suspend fun getCitiesWithStores(): List<CityWithStoresEntity> =
        cityWithStoreEntityMap.values.toList()

    override suspend fun insert(vararg store: StoreEntity) {
        store.forEach { store ->
            storeEntityMap[store.storeId] = store
            store.cityLocatorId?.let { cityId ->
                cityWithStoreEntityMap[cityId]?.stores?.add(store)
            }
        }
    }

    override suspend fun insertCity(vararg city: CityEntity) {
        city.forEach { city ->
            cityEntityMap[city.cityId] = city
            cityWithStoreEntityMap[city.cityId] =
                CityWithStoresEntity(
                    city,
                    storeEntityMap.values.filter { it.cityLocatorId == city.cityId }.toMutableList()
                )
        }
    }
}
