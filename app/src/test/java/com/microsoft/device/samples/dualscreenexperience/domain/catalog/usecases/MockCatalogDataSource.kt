/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.catalog.usecases

import com.microsoft.device.samples.dualscreenexperience.data.catalog.CatalogDataSource
import com.microsoft.device.samples.dualscreenexperience.data.catalog.model.CatalogItemEntity
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogViewType

class MockCatalogDataSource : CatalogDataSource {
    override suspend fun getAll(): List<CatalogItemEntity> = listOf(catalogItemEntity)
}

const val CATALOG_VIEW_TYPE_LAYOUT1_KEY = 1

val catalogItemEntity = CatalogItemEntity(
    1,
    "Mock Catalog Item",
    CATALOG_VIEW_TYPE_LAYOUT1_KEY,
    "description 1",
    "description 2",
    "description 3",
    "description 4",
    "description 5",
    "image 1",
    "image 2",
    "image 3",
)

val catalogItem = CatalogItem(
    1,
    "Mock Catalog Item",
    CatalogViewType.Layout1,
    "description 1",
    "description 2",
    "description 3",
    "description 4",
    "description 5",
    "image 1",
    "image 2",
    "image 3"
)
