/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.catalog.usecases

import com.microsoft.device.display.sampleheroapp.data.catalog.CatalogItemDataSource
import com.microsoft.device.display.sampleheroapp.domain.catalog.model.CatalogItem
import javax.inject.Inject

class GetCatalogListUseCase @Inject constructor(
    private val catalogItemRepository: CatalogItemDataSource
) {
    suspend fun getAll(): List<CatalogItem> = catalogItemRepository.getAll()
}
