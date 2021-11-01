/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.store.usecases

import com.microsoft.device.samples.dualscreenexperience.data.store.StoreDataSource
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.Store
import javax.inject.Inject

class GetStoresByCityUseCase @Inject constructor(private val storeRepository: StoreDataSource) {
    suspend fun getAll(cityName: String): List<Store> =
        storeRepository.getCitiesWithStores()
            .filter { it.city.name == cityName }
            .map { it.stores }
            .flatten()
            .map { Store(it) }
}
