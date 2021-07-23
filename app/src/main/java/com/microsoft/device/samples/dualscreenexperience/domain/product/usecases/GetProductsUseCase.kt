/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.product.usecases

import com.microsoft.device.samples.dualscreenexperience.data.product.ProductDataSource
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.Product
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val productRepository: ProductDataSource) {
    suspend fun getAll(): List<Product> = productRepository.getAll().map { Product(it) }
}
