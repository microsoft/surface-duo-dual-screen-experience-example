/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.store.model

import com.microsoft.device.samples.dualscreenexperience.data.store.model.StoreEntity

data class Store(
    val storeId: Long,
    val name: String,
    val address: String,
    val cityId: Long?,
    val cityStateCode: String,
    val phoneNumber: String,
    val emailAddress: String,
    val lat: Double,
    val lng: Double,
    val rating: Float,
    val reviewCount: Int,
    val imagePath: String
) {
    constructor(entity: StoreEntity) :
        this(
            entity.storeId,
            entity.name,
            entity.address,
            entity.cityLocatorId,
            entity.cityStateCode,
            entity.phoneNumber,
            entity.emailAddress,
            entity.lat,
            entity.lng,
            entity.rating,
            entity.reviewCount,
            entity.imagePath
        )

    fun toMapMarkerModel(): MapMarkerModel = MapMarkerModel(name, MarkerType.PIN, lat, lng, storeId)
}
