/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.catalog.model

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
