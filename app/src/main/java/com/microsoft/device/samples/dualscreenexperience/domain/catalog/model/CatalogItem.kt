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
    val viewType: CatalogPage,
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
        viewType = CatalogPage.get(this.viewType),
        primaryDescription = this.primaryDescription,
        secondaryDescription = this.secondaryDescription,
        thirdDescription = this.thirdDescription,
        fourthDescription = this.fourthDescription,
        fifthDescription = this.fifthDescription,
        firstPicture = this.firstPicture,
        secondPicture = this.secondPicture,
        thirdPicture = this.thirdPicture
    )

enum class CatalogPage {
    Page1,
    Page2,
    Page3,
    Page4,
    Page5,
    Page6,
    Page7;

    companion object {
        fun get(typeId: Int?) = values().first { it.ordinal + 1 == typeId }
    }
}
