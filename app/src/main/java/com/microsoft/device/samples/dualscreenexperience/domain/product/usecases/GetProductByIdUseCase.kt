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

class GetProductByIdUseCase @Inject constructor(
    private val productRepository: ProductDataSource
) {
    suspend fun getById(productId: Long): Product? =
        productRepository.getById(productId)?.let { Product(it) }
}
