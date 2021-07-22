/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.store.testutil

import com.microsoft.device.samples.dualscreenexperience.data.store.model.CityEntity
import com.microsoft.device.samples.dualscreenexperience.data.store.model.StoreEntity
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.City
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MapMarkerModel
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.MarkerType
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.Store
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.StoreImage
import com.microsoft.maps.Geopoint

val storeEntity = StoreEntity(
    102,
    "Joy's",
    "1204 NE 36th St",
    10,
    "Redmond, WA 98134",
    "(206)-473-3864",
    "musicals@joy.com",
    47.640923,
    -122.138567,
    4.6f,
    86,
    1
)

val cityEntity = CityEntity(
    10,
    "Redmond",
    true,
    47.6377438,
    -122.1224222
)

val hiddenCityEntity = CityEntity(
    11,
    "Seattle",
    false,
    47.6377438,
    -122.1224222
)

val store = Store(
    102,
    "Joy's",
    "1204 NE 36th St",
    10,
    "Redmond, WA 98134",
    "(206)-473-3864",
    "musicals@joy.com",
    47.640923,
    -122.138567,
    4.6f,
    86,
    StoreImage.JOY
)

val city = City(
    "Redmond",
    true,
    47.6377438,
    -122.1224222
)

val storeMarkerModel = MapMarkerModel(
    "Joy's",
    MarkerType.PIN,
    47.640923,
    -122.138567,
    102
)

val storeCenterMarkerModel = MapMarkerModel(
    "",
    MarkerType.CENTER,
    47.640923,
    -122.138567
)

val cityMarkerModel = MapMarkerModel(
    "Redmond",
    MarkerType.CIRCLE,
    47.6377438,
    -122.1224222
)

val storeCityCenterMarkerModel = MapMarkerModel(
    "",
    MarkerType.CENTER,
    47.6393334,
    -122.13049459999999
)

val cityGeopoint = Geopoint(
    47.6377438,
    -122.1224222
)

const val JOY_KEY = 1
const val CESAR_KEY = 2
const val HAKON_KEY = 3
const val BIANCA_KEY = 4
const val GUY_KEY = 5
const val CRISTIAN_KEY = 6
const val MEHUL_KEY = 7
