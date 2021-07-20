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

fun buildCenterMarker(markers: List<MapMarkerModel>): MapMarkerModel {
    val latList = markers.takeIf { it.isNotEmpty() }?.map { it.lat }
    val lngList = markers.takeIf { it.isNotEmpty() }?.map { it.lng }
    val maxMinLatList = listOf(latList?.maxOrNull() ?: 0.0, latList?.minOrNull() ?: 0.0)
    val maxMinLngList = listOf(lngList?.maxOrNull() ?: 0.0, lngList?.minOrNull() ?: 0.0)
    return MapMarkerModel(
        "",
        MarkerType.CENTER,
        maxMinLatList.average(),
        maxMinLngList.average()
    )
}

class MapIconSelectable(var isSelected: Boolean = false) : MapIcon()
