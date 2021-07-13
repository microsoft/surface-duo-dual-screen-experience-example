/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.catalog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CatalogItem(
    val itemId: Long,
    val name: String,
    val viewType: ViewType,
    val primaryDescription: String,
    val secondaryDescription: String?,
    val firstPicture: String,
    val secondPicture: String?,
    val thirdPicture: String?
) : Parcelable

enum class ViewType(var typeId: Int) {

    // Layout with one long text above, 3 images, another long text below
    Layout1(1),

    // Layout with image-text, long text, two images
    Layout2(2),

    // Layout with two groups of image-text, mirrored
    Layout3(3),

    // Layout with two images, long text, image-text
    Layout4(4),

    // Layout with one image and one long text
    Layout5(5);

    companion object {
        fun get(typeId: Int?) = values().first { it.typeId == typeId }
    }
}
