/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.store.model

data class StoreEntity(
    val storeId: Long,
    val name: String,
    val address: String,
    val cityLocatorId: Long?,
    val cityStateCode: String,
    val phoneNumber: String,
    val emailAddress: String,
    val lat: Double,
    val lng: Double,
    val description: String,
    val rating: Float,
    val reviewCount: Int,
    val imageId: Int
)
