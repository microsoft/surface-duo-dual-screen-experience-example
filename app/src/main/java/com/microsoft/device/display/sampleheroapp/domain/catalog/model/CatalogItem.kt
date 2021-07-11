/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.catalog.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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
    Layout1(1),
    Layout2(2),
    Layout3(3),
    Layout4(4),
    Layout5(5);

    companion object {
        fun get(typeId: Int?) = values().first { it.typeId == typeId }
    }
}
