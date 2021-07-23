/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.store.usecases

import com.microsoft.device.samples.dualscreenexperience.data.store.StoreDataSource
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.City
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MapMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.Store
import javax.inject.Inject

class GetMapMarkersUseCase @Inject constructor(private val storeRepository: StoreDataSource) {
    suspend fun getAll(): List<MapMarkerModel> {
        val cityWithStores = storeRepository.getCitiesWithStores()
        val stores = storeRepository.getAll().map { Store(it).toMapMarkerModel() }
        val cities =
            cityWithStores
                .filter { it.city.isDisplayed && it.stores.isNotEmpty() }
                .map { City(it.city).toMapMarkerModel() }

        return cities + stores
    }
}
