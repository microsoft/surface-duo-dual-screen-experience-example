/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.catalog.usecases

import com.microsoft.device.samples.dualscreenexperience.data.catalog.CatalogItemDataSource
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.toCatalogItem
import javax.inject.Inject

class GetCatalogListUseCase @Inject constructor(
    private val catalogItemRepository: CatalogItemDataSource
) {
    suspend fun getAll(): List<CatalogItem> =
        catalogItemRepository.getAll().map { it.toCatalogItem() }
}
