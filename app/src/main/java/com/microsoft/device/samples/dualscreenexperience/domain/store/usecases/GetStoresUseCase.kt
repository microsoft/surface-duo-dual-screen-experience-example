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

class GetStoresUseCase @Inject constructor(private val storeRepository: StoreDataSource) {
    suspend fun getAll(): List<Store> = storeRepository.getAll().map { Store(it) }
    suspend fun getById(storeId: Long): Store? = storeRepository.getById(storeId)?.let { Store(it) }
}
