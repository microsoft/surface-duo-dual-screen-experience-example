/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.store

import com.microsoft.device.samples.dualscreenexperience.data.store.model.CityEntity
import com.microsoft.device.samples.dualscreenexperience.data.store.model.StoreEntity

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
