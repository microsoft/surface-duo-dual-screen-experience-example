/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.product

import com.microsoft.device.samples.dualscreenexperience.data.product.model.ProductEntity
import com.microsoft.device.samples.dualscreenexperience.data.product.remote.ProductRemoteDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductDataSource {

    override suspend fun getAll(): List<ProductEntity> = remoteDataSource.getAll()

    override suspend fun getById(productId: Long): ProductEntity? = remoteDataSource.getById(productId)
}
