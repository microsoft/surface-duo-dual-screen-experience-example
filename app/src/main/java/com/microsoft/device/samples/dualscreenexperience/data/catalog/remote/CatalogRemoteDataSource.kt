/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.catalog.remote

import android.content.res.AssetManager
import com.google.gson.Gson
import com.microsoft.device.samples.dualscreenexperience.config.RemoteDataSourceConfig
import com.microsoft.device.samples.dualscreenexperience.data.catalog.CatalogDataSource
import com.microsoft.device.samples.dualscreenexperience.data.catalog.model.CatalogItemEntity
import com.microsoft.device.samples.dualscreenexperience.data.catalog.model.CatalogItemList
import com.microsoft.device.samples.dualscreenexperience.data.getJsonDataFromAsset
import javax.inject.Inject

class CatalogRemoteDataSource @Inject constructor(
    private val assetManager: AssetManager,
    private val gson: Gson
) : CatalogDataSource {

    override suspend fun getAll(): List<CatalogItemEntity> =
        gson.fromJson(
            getJsonDataFromAsset(assetManager, RemoteDataSourceConfig.catalogItemsFileName),
            CatalogItemList::class.java
        ).catalogItems
}
