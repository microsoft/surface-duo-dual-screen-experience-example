/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.product

import com.microsoft.device.display.sampleheroapp.data.product.local.ProductLocalDataSource
import com.microsoft.device.display.sampleheroapp.data.product.local.model.Product

class ProductRepository(
    private val localDataSource: ProductLocalDataSource
) : ProductDataSource {

    override suspend fun getAll(): List<Product>? = localDataSource.getAll()
}
