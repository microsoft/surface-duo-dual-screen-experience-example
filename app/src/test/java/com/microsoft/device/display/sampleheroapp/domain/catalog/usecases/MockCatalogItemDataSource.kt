/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.catalog.usecases

import com.microsoft.device.display.sampleheroapp.data.catalog.CatalogItemDataSource
import com.microsoft.device.display.sampleheroapp.data.catalog.model.CatalogItemEntity
import com.microsoft.device.display.sampleheroapp.domain.catalog.model.CatalogItem
import com.microsoft.device.display.sampleheroapp.domain.catalog.model.CatalogViewType

class MockCatalogItemDataSource : CatalogItemDataSource {
    override suspend fun getAll(): List<CatalogItemEntity> = listOf(catalogItemEntity)
}

const val CATALOG_VIEW_TYPE_LAYOUT1_KEY = 1

val catalogItemEntity = CatalogItemEntity(
    1,
    "Mock Catalog Item",
    CATALOG_VIEW_TYPE_LAYOUT1_KEY,
    "description 1",
    "description 2",
    "image 1",
    "image 2",
    "image 3"
)

val catalogItem = CatalogItem(
    1,
    "Mock Catalog Item",
    CatalogViewType.Layout1,
    "description 1",
    "description 2",
    "image 1",
    "image 2",
    "image 3"
)
