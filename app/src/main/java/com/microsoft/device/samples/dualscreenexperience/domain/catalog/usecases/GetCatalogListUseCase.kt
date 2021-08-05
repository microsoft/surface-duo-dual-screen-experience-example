/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.catalog.usecases

import com.microsoft.device.samples.dualscreenexperience.data.catalog.CatalogDataSource
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.toCatalogItem
import javax.inject.Inject

class GetCatalogListUseCase @Inject constructor(
    private val catalogRepository: CatalogDataSource
) {
    suspend fun getAll(): List<CatalogItem> = catalogRepository.getAll().map { it.toCatalogItem() }
}
