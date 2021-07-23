/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.catalog

import android.content.res.AssetManager
import com.google.gson.Gson
import com.microsoft.device.samples.dualscreenexperience.config.CatalogConfig
import com.microsoft.device.samples.dualscreenexperience.data.catalog.model.CatalogItemEntity
import com.microsoft.device.samples.dualscreenexperience.data.catalog.model.CatalogItemList
import com.microsoft.device.samples.dualscreenexperience.data.getJsonDataFromAsset
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogItemRepository @Inject constructor(
    private val assetManager: AssetManager,
    private val gson: Gson
) : CatalogItemDataSource {

    override suspend fun getAll(): List<CatalogItemEntity> =
        gson.fromJson(
            getJsonDataFromAsset(assetManager, CatalogConfig.catalogItemsFileName),
            CatalogItemList::class.java
        ).catalogItems
}
