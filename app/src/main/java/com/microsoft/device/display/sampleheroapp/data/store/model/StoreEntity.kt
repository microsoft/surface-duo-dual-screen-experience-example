/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.store.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stores")
data class StoreEntity(
    @PrimaryKey val storeId: Long,
    val name: String,
    val address: String,
    val cityLocatorId: Long?,
    val cityStateCode: String,
    val phoneNumber: String,
    val emailAddress: String,
    val lat: Double,
    val lng: Double,
    val rating: Float,
    val reviewCount: Int,
    val imageId: Int
)
