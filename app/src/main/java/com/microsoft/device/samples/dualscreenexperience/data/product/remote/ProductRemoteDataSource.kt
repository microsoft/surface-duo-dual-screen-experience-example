/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.product.remote

import android.content.res.AssetManager
import com.google.gson.Gson
import com.microsoft.device.samples.dualscreenexperience.config.RemoteDataSourceConfig
import com.microsoft.device.samples.dualscreenexperience.data.getJsonDataFromAsset
import com.microsoft.device.samples.dualscreenexperience.data.product.ProductDataSource
import com.microsoft.device.samples.dualscreenexperience.data.product.model.ProductEntity
import com.microsoft.device.samples.dualscreenexperience.data.product.model.ProductItemList
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val assetManager: AssetManager,
    private val gson: Gson
) : ProductDataSource {

    override suspend fun getAll(): List<ProductEntity> =
        gson.fromJson(
            getJsonDataFromAsset(assetManager, RemoteDataSourceConfig.productItemsFileName),
            ProductItemList::class.java
        ).productItems

    override suspend fun getById(productId: Long): ProductEntity? =
        gson.fromJson(
            getJsonDataFromAsset(assetManager, RemoteDataSourceConfig.productItemsFileName),
            ProductItemList::class.java
        ).productItems.firstOrNull { it.productId == productId }
}
