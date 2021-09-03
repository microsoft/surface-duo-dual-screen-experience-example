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
    val image: StoreImage?
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
            StoreImage.get(entity.imageId)
        )

    fun toMapMarkerModel(): MapMarkerModel =
        MapMarkerModel(name, MarkerType.PIN, lat, lng, cityId != null, storeId)
}

enum class StoreImage(var imageId: Int) {
    QUINN(1),
    ANA(2),
    SERGIO(3),
    OVE(4),
    NATASHA(5),
    BEN(6),
    KRISTIAN(7);

    companion object {
        fun get(imageId: Int): StoreImage? = values().firstOrNull { it.imageId == imageId }
    }
}
