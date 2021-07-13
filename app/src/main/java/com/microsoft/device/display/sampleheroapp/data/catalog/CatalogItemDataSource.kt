/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.catalog

import com.microsoft.device.display.sampleheroapp.data.catalog.model.CatalogItemEntity

interface CatalogItemDataSource {
    suspend fun getAll(): List<CatalogItemEntity>
}
