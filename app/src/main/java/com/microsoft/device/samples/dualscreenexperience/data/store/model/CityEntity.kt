/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.store.model

data class CityEntity(
    var cityId: Long,
    val name: String,
    val isDisplayed: Boolean,
    val lat: Double,
    val lng: Double
)
