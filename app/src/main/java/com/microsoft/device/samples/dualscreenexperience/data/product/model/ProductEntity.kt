/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.product.model

data class ProductEntity(
    val productId: Long,
    val name: String,
    val price: Int,
    val description: String,
    val rating: Float,
    val fretsNumber: Int,
    val deliveryDays: Int,
    val typeId: Int,
    val colorId: Int
)
