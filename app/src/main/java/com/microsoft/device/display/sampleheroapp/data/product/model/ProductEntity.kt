/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.product.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    val name: String,
    val price: Int,
    val description: String,
    val rating: Float
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
