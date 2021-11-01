/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.map

interface OnMapClickListener {
    fun onMarkerClicked(markerName: String?, isAlreadySelected: Boolean)
    fun onNoMarkerClicked()
}
