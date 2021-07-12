/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.catalog.usecases

import com.microsoft.device.display.sampleheroapp.data.catalog.CatalogItemDataSource
import com.microsoft.device.display.sampleheroapp.domain.catalog.model.CatalogItem
import com.microsoft.device.display.sampleheroapp.domain.catalog.model.ViewType

class MockCatalogItemDataSource : CatalogItemDataSource {
    override suspend fun getAll(): List<CatalogItem> = listOf(catalogItem)
}

val catalogItem = CatalogItem(
    1,
    "Mock Catalog Item",
    ViewType.Layout1,
    "description 1",
    "description 2",
    "image 1",
    "image 2",
    "image 3"
)
