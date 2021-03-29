/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.product.usecases

import com.microsoft.device.display.sampleheroapp.data.product.ProductDataSource
import com.microsoft.device.display.sampleheroapp.domain.product.model.Product
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val productRepository: ProductDataSource
) {
    suspend fun getById(productId: Long): Product? =
        productRepository.getById(productId)?.let { Product(it) }
}
