/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.store.model

data class MapMarkerModel(
    val name: String,
    val type: MarkerType,
    val lat: Double,
    val lng: Double,
    val id: Long = 0
)
