/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.catalog.model

import android.os.Parcelable
import com.microsoft.device.samples.dualscreenexperience.data.catalog.model.CatalogItemEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatalogItem(
    val itemId: Long,
    val name: String,
    val viewType: CatalogViewType,
    val primaryDescription: String,
    val secondaryDescription: String?,
    val thirdDescription: String?,
    val fourthDescription: String?,
    val fifthDescription: String?,
    val firstPicture: String?,
    val secondPicture: String?,
    val thirdPicture: String?
) : Parcelable

fun CatalogItemEntity.toCatalogItem() =
    CatalogItem(
        itemId = this.itemId,
        name = this.name,
        viewType = CatalogViewType.get(this.viewType),
        primaryDescription = this.primaryDescription,
        secondaryDescription = this.secondaryDescription,
        thirdDescription = this.thirdDescription,
        fourthDescription = this.fourthDescription,
        fifthDescription = this.fifthDescription,
        firstPicture = this.firstPicture,
        secondPicture = this.secondPicture,
        thirdPicture = this.thirdPicture
    )

enum class CatalogViewType(var typeId: Int) {

    // Layout with Table Of Contents
    Layout1(1),

    // Layout with image-text, long text, two images
    Layout2(2),

    // Layout with two groups of image-text, mirrored
    Layout3(3),

    // Layout with two images, long text
    Layout4(4),

    // Layout with text, one small image, one large image and another text
    Layout5(5),

    // Layout with text, one large image and one long text
    Layout6(6),

    // Layout with two groups of image-text
    Layout7(7);

    companion object {
        fun get(typeId: Int?) = values().first { it.typeId == typeId }
    }
}
