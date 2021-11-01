/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.catalog.model

data class CatalogItemEntity(
    val itemId: Long,
    val name: String,
    val viewType: Int,
    val primaryDescription: String,
    val secondaryDescription: String?,
    val thirdDescription: String?,
    val fourthDescription: String?,
    val fifthDescription: String?,
    val firstPicture: String?,
    val secondPicture: String,
    val thirdPicture: String?
)
