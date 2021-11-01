/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.store.remote

import android.content.res.AssetManager
import com.google.gson.Gson
import com.microsoft.device.samples.dualscreenexperience.config.RemoteDataSourceConfig
import com.microsoft.device.samples.dualscreenexperience.data.getJsonDataFromAsset
import com.microsoft.device.samples.dualscreenexperience.data.store.StoreDataSource
import com.microsoft.device.samples.dualscreenexperience.data.store.model.CityStoreList
import com.microsoft.device.samples.dualscreenexperience.data.store.model.CityWithStoresEntity
import com.microsoft.device.samples.dualscreenexperience.data.store.model.StoreEntity
import javax.inject.Inject

class StoreRemoteDataSource @Inject constructor(
    private val assetManager: AssetManager,
    private val gson: Gson
) : StoreDataSource {

    override suspend fun getAll(): List<StoreEntity> =
        gson.fromJson(
            getJsonDataFromAsset(assetManager, RemoteDataSourceConfig.cityStoreItemsFileName),
            CityStoreList::class.java
        ).storeItems

    override suspend fun getById(storeId: Long): StoreEntity? =
        gson.fromJson(
            getJsonDataFromAsset(assetManager, RemoteDataSourceConfig.cityStoreItemsFileName),
            CityStoreList::class.java
        ).storeItems.firstOrNull { it.storeId == storeId }

    override suspend fun getCitiesWithStores(): List<CityWithStoresEntity> {
        val cityStoreList = gson.fromJson(
            getJsonDataFromAsset(assetManager, RemoteDataSourceConfig.cityStoreItemsFileName),
            CityStoreList::class.java
        )
        return cityStoreList.cityItems.map { city ->
            CityWithStoresEntity(
                city,
                cityStoreList.storeItems.filter { it.cityLocatorId == city.cityId }.toMutableList()
            )
        }
    }
}
