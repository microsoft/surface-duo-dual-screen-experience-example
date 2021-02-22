/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.store.model

import com.microsoft.device.display.sampleheroapp.data.store.model.CityEntity

data class City(val name: String, val isDisplayed: Boolean, val lat: Double, val lng: Double) {
    constructor(entity: CityEntity) : this(entity.name, entity.isDisplayed, entity.lat, entity.lng)

    fun toMapMarkerModel(): MapMarkerModel = MapMarkerModel(name, MarkerType.CIRCLE, lat, lng)
}
