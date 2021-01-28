/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.product.local

import com.microsoft.device.display.sampleheroapp.data.product.ProductDataSource
import com.microsoft.device.display.sampleheroapp.data.product.local.model.Product

class ProductLocalDataSource(
    private val productDao: ProductDao
) : ProductDataSource {

    override suspend fun getAll(): List<Product>? = productDao.getAll()
}
