/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.product.model

import com.microsoft.device.display.sampleheroapp.data.product.model.ProductEntity

data class Product(val name: String, val price: Int, val description: String) {
    constructor(entity: ProductEntity) : this(entity.name, entity.price, entity.description)
}
