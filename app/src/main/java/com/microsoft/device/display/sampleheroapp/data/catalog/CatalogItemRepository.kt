/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.catalog

import android.content.res.AssetManager
import com.google.gson.Gson
import com.microsoft.device.display.sampleheroapp.config.CatalogConfig
import com.microsoft.device.display.sampleheroapp.data.catalog.model.CatalogItemList
import com.microsoft.device.display.sampleheroapp.data.catalog.model.toCatalogItem
import com.microsoft.device.display.sampleheroapp.data.getJsonDataFromAsset
import com.microsoft.device.display.sampleheroapp.domain.catalog.model.CatalogItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogItemRepository @Inject constructor(
    private val assetManager: AssetManager,
    private val gson: Gson
) : CatalogItemDataSource {

    override suspend fun getAll(): List<CatalogItem> =
        gson.fromJson(
            getJsonDataFromAsset(assetManager, CatalogConfig.catalogItemsFileName),
            CatalogItemList::class.java
        ).catalogItems
            .map { it.toCatalogItem() }
}