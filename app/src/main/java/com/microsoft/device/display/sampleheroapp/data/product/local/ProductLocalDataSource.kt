/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.product.local

import com.microsoft.device.display.sampleheroapp.data.product.ProductDataSource
import com.microsoft.device.display.sampleheroapp.data.product.model.ProductEntity
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(
    private val productDao: ProductDao
) : ProductDataSource {

    override suspend fun getAll(): List<ProductEntity>? = productDao.getAll()
}
