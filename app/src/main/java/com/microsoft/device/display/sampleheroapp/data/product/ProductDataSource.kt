/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.product

import com.microsoft.device.display.sampleheroapp.data.product.model.ProductEntity

interface ProductDataSource {
    suspend fun getAll(): List<ProductEntity>?
}
