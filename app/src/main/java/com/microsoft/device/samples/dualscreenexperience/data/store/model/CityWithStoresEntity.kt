/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.store.model

data class CityWithStoresEntity(
    val city: CityEntity,
    val stores: MutableList<StoreEntity>
)
