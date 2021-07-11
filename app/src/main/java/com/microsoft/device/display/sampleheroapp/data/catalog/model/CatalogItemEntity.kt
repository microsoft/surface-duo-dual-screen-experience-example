/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.catalog.model

import com.microsoft.device.display.sampleheroapp.domain.catalog.model.CatalogItem
import com.microsoft.device.display.sampleheroapp.domain.catalog.model.ViewType

data class CatalogItemEntity(
    val itemId: Long,
    val name: String,
    val viewType: Int,
    val primaryDescription: String,
    val secondaryDescription: String,
    val firstPicture: String,
    val secondPicture: String,
    val thirdPicture: String?
)

fun CatalogItemEntity.toDto() =
    CatalogItem(
        itemId = this.itemId,
        name = this.name,
        viewType = ViewType.get(this.viewType),
        primaryDescription = this.primaryDescription,
        secondaryDescription = this.secondaryDescription,
        firstPicture = this.firstPicture,
        secondPicture = this.secondPicture,
        thirdPicture = this.thirdPicture
    )
