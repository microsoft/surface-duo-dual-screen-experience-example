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

fun MapMarkerModel.toLatLng(): LatLng = LatLng(lat, lng)

fun Projection.isInBounds(marker: MapMarkerModel): Boolean =
    visibleRegion.latLngBounds.isInBounds(marker)

fun LatLngBounds.isInBounds(marker: MapMarkerModel): Boolean = contains(marker.toLatLng())

class MarkerSelectable(var googleModel: Marker, var isSelected: Boolean = false)
