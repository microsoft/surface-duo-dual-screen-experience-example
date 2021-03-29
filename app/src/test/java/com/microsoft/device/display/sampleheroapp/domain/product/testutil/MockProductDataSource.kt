/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.product.testutil

import com.microsoft.device.display.sampleheroapp.data.product.ProductDataSource
import com.microsoft.device.display.sampleheroapp.data.product.model.ProductEntity

class MockProductDataSource : ProductDataSource {
    private var productEntityMap = mutableMapOf<Long, ProductEntity>()

    override suspend fun getAll(): List<ProductEntity> = productEntityMap.values.toList()

    override suspend fun getById(productId: Long): ProductEntity? = productEntityMap[productId]

    override suspend fun insert(vararg products: ProductEntity) {
        products.forEach { product ->
            productEntityMap[product.productId] = product
        }
    }
}
