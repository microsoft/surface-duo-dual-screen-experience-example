/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.map

import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MapMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MarkerType

fun MapMarkerModel.toLatLng(): LatLng = LatLng(lat, lng)

fun Projection.isInBounds(marker: MapMarkerModel): Boolean = visibleRegion.latLngBounds.isInBounds(marker)

fun LatLngBounds.isInBounds(marker: MapMarkerModel): Boolean = contains(marker.toLatLng())

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

class MarkerSelectable(var googleModel: Marker, var isSelected: Boolean = false)
