/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.product.local

import com.microsoft.device.samples.dualscreenexperience.data.product.ProductDataSource
import com.microsoft.device.samples.dualscreenexperience.data.product.model.ProductEntity
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(
    private val productDao: ProductDao
) : ProductDataSource {

    override suspend fun getAll(): List<ProductEntity> = productDao.getAll()

    override suspend fun getById(productId: Long) = productDao.getById(productId)

    override suspend fun insert(vararg products: ProductEntity) {
        productDao.insert(*products)
    }
}
