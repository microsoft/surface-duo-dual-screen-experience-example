/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.store.model

import androidx.room.Embedded
import androidx.room.Relation

data class CityWithStoresEntity(
    @Embedded val city: CityEntity,
    @Relation(
        parentColumn = "cityId",
        entityColumn = "cityLocatorId"
    )
    val stores: MutableList<StoreEntity>
)
