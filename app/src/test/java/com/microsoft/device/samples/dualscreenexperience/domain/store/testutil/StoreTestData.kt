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
import com.microsoft.maps.Geopoint

val storeEntity = StoreEntity(
    102,
    "Ana's",
    "4568 Main St",
    10,
    "Redmond, WA 98053",
    "(206)-555-0101",
    "ana@fabrikam.com",
    47.64304736313635,
    -122.13130676286585,
    4.6f,
    86,
    "stores/ana_store.png"
)

val cityEntity = CityEntity(
    10,
    "Redmond",
    true,
    47.672122955322266,
    -122.13571166992188
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
    "Ana's",
    "4568 Main St",
    10,
    "Redmond, WA 98053",
    "(206)-555-0101",
    "ana@fabrikam.com",
    47.64304736313635,
    -122.13130676286585,
    4.6f,
    86,
    "stores/ana_store.png"
)

val city = City(
    "Redmond",
    true,
    47.672122955322266,
    -122.13571166992188
)

val storeMarkerModel = MapMarkerModel(
    "Ana's",
    MarkerType.PIN,
    47.64304736313635,
    -122.13130676286585,
    102
)

val storeCenterMarkerModel = MapMarkerModel(
    "",
    MarkerType.CENTER,
    47.64304736313635,
    -122.13130676286585,
)

val cityMarkerModel = MapMarkerModel(
    "Redmond",
    MarkerType.CIRCLE,
    47.672122955322266,
    -122.13571166992188
)

val storeCityCenterMarkerModel = MapMarkerModel(
    "",
    MarkerType.CENTER,
    47.657585159229308,
    -122.133509216393865
)

val cityGeopoint = Geopoint(
    47.672122955322266,
    -122.13571166992188
)
