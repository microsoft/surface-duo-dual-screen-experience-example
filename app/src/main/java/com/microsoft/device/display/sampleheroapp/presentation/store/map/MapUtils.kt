/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store.map

import com.microsoft.device.display.sampleheroapp.domain.store.model.MapMarkerModel
import com.microsoft.device.display.sampleheroapp.domain.store.model.MarkerType
import com.microsoft.maps.GeoboundingBox
import com.microsoft.maps.Geopoint
import com.microsoft.maps.MapIcon

fun MapMarkerModel.toGeopoint(): Geopoint = Geopoint(lat, lng)

fun GeoboundingBox.isInBounds(marker: MapMarkerModel): Boolean =
    (marker.lng in west..east && marker.lat in south..north)

fun buildCenterMarker(markers: List<MapMarkerModel>) =
    MapMarkerModel(
        "",
        MarkerType.CENTER,
        markers.takeIf { it.isNotEmpty() }?.map { it.lat }?.average() ?: 0.0,
        markers.takeIf { it.isNotEmpty() }?.map { it.lng }?.average() ?: 0.0
    )

class MapIconSelectable(var isSelected: Boolean = false) : MapIcon()
