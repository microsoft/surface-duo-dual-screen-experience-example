/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.product

import com.microsoft.device.display.sampleheroapp.data.product.local.ProductLocalDataSource
import com.microsoft.device.display.sampleheroapp.data.product.model.ProductEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val localDataSource: ProductLocalDataSource
) : ProductDataSource {

    override suspend fun getAll(): List<ProductEntity> = localDataSource.getAll()
}
