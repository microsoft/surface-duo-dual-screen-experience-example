/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.catalog

import com.microsoft.device.samples.dualscreenexperience.data.catalog.model.CatalogItemEntity
import com.microsoft.device.samples.dualscreenexperience.data.catalog.remote.CatalogRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogRepository @Inject constructor(
    private val catalogRemoteDataSource: CatalogRemoteDataSource
) : CatalogDataSource {

    override suspend fun getAll(): List<CatalogItemEntity> = catalogRemoteDataSource.getAll()
}
