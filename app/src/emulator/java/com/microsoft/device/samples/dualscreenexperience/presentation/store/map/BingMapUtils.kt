/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.map

import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MapMarkerModel
import com.microsoft.maps.GeoboundingBox
import com.microsoft.maps.Geopoint
import com.microsoft.maps.MapIcon

fun MapMarkerModel.toGeopoint(): Geopoint = Geopoint(lat, lng)

fun GeoboundingBox.isInBounds(marker: MapMarkerModel): Boolean =
    (marker.lng in west..east && marker.lat in south..north)

class MapIconSelectable(val bingModel: MapIcon, var isSelected: Boolean = false)
