/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.store

import com.microsoft.device.samples.dualscreenexperience.data.store.model.CityWithStoresEntity
import com.microsoft.device.samples.dualscreenexperience.data.store.model.StoreEntity
import com.microsoft.device.samples.dualscreenexperience.data.store.remote.StoreRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoreRepository @Inject constructor(
    private val remoteDataSource: StoreRemoteDataSource
) : StoreDataSource {

    override suspend fun getAll(): List<StoreEntity> = remoteDataSource.getAll()

    override suspend fun getById(storeId: Long): StoreEntity? = remoteDataSource.getById(storeId)

    override suspend fun getCitiesWithStores(): List<CityWithStoresEntity> =
        remoteDataSource.getCitiesWithStores()
}
