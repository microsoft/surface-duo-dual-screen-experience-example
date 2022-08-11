/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.ContentTextItem
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.PageLayout
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.contentDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.util.sizeOrZero

const val FIRST_PAGE_FIRST_TEXT_ID = "firstText"
const val FIRST_PAGE_SECOND_TEXT_ID = "secondText"
const val FIRST_PAGE_THIRD_TEXT_ID = "thirdText"
const val FIRST_PAGE_FOURTH_TEXT_ID = "fourthText"
const val FIRST_PAGE_FIFTH_TEXT_ID = "fifthText"

@Composable
fun CatalogFirstPage(
    modifier: Modifier = Modifier,
    catalogList: List<CatalogItem>,
    onItemClick: (Int) -> Unit
) {
    val pageNumberOrdinal = CatalogPage.Page1.ordinal
    val catalogItem = catalogList[pageNumberOrdinal]

    PageLayout(
        modifier,
        pageNumberOrdinal + 1,
        catalogList.sizeOrZero()
    ) {
        TableOfContents(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            catalogItem = catalogItem,
            onItemClick = onItemClick
        )
    }
}

@Composable
private fun getConstraintSetForTableOfContents() = ConstraintSet {
    val firstTextRef = createRefFor(FIRST_PAGE_FIRST_TEXT_ID)
    val secondTextRef = createRefFor(FIRST_PAGE_SECOND_TEXT_ID)
    val thirdTextRef = createRefFor(FIRST_PAGE_THIRD_TEXT_ID)
    val fourthTextRef = createRefFor(FIRST_PAGE_FOURTH_TEXT_ID)
    val fifthTextRef = createRefFor(FIRST_PAGE_FIFTH_TEXT_ID)

    constrain(firstTextRef) {
        start.linkTo(parent.start)
        top.linkTo(parent.top)
    }

    constrain(secondTextRef) {
        end.linkTo(parent.end)
        start.linkTo(parent.start)
        top.linkTo(firstTextRef.bottom)
    }

    constrain(thirdTextRef) {
        end.linkTo(parent.end)
        start.linkTo(parent.start)
        top.linkTo(secondTextRef.bottom)
    }

    constrain(fourthTextRef) {
        end.linkTo(parent.end)
        start.linkTo(parent.start)
        top.linkTo(thirdTextRef.bottom)
    }

    constrain(fifthTextRef) {
        end.linkTo(parent.end)
        start.linkTo(parent.start)
        top.linkTo(fourthTextRef.bottom)
    }
}

@Composable
fun TableOfContents(
    modifier: Modifier = Modifier,
    catalogItem: CatalogItem,
    onItemClick: (Int) -> Unit
) {
    val constraintSet = getConstraintSetForTableOfContents()

    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    top = dimensionResource(id = R.dimen.catalog_margin_very_large),
                    end = dimensionResource(id = R.dimen.catalog_horizontal_margin)
                )
                .layoutId(FIRST_PAGE_FIRST_TEXT_ID),
            text = catalogItem.primaryDescription,
            style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.onSurface)
        )

        ContentTextItem(
            modifier = Modifier
                .contentDescription(
                    getPageContentDescription(
                        catalogItem.secondaryDescription ?: "",
                        stringResource(id = R.string.catalog_toc_item_content_description)
                    )
                )
                .layoutId(FIRST_PAGE_SECOND_TEXT_ID),
            text = catalogItem.secondaryDescription ?: "",
            destinationPage = CatalogPage.Page2.ordinal + 1,
            onItemClick = onItemClick
        )

        ContentTextItem(
            modifier = Modifier
                .contentDescription(
                    getPageContentDescription(
                        catalogItem.thirdDescription ?: "",
                        stringResource(id = R.string.catalog_toc_item_content_description)
                    )
                )
                .layoutId(FIRST_PAGE_THIRD_TEXT_ID),
            text = catalogItem.thirdDescription ?: "",
            destinationPage = CatalogPage.Page3.ordinal + 1,
            onItemClick = onItemClick
        )

        ContentTextItem(
            modifier = Modifier
                .contentDescription(
                    getPageContentDescription(
                        catalogItem.fourthDescription ?: "",
                        stringResource(id = R.string.catalog_toc_item_content_description)
                    )
                )
                .layoutId(FIRST_PAGE_FOURTH_TEXT_ID),
            text = catalogItem.fourthDescription ?: "",
            destinationPage = CatalogPage.Page4.ordinal + 1,
            onItemClick = onItemClick
        )

        ContentTextItem(
            modifier = Modifier
                .contentDescription(
                    getPageContentDescription(
                        catalogItem.fifthDescription ?: "",
                        stringResource(id = R.string.catalog_toc_item_content_description)
                    )
                )
                .layoutId(FIRST_PAGE_FIFTH_TEXT_ID),
            text = catalogItem.fifthDescription ?: "",
            destinationPage = CatalogPage.Page6.ordinal + 1,
            onItemClick = onItemClick
        )
    }
}

fun getPageContentDescription(value: String, replacement: String): String {
    val titleIndex = value.indexOf('.')
    val pageNumberIndex = value.lastIndexOf('.') + 1

    return value.replaceRange(titleIndex, pageNumberIndex, replacement)
}
