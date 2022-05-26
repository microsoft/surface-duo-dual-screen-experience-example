/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.rememberAsyncImagePainter
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.BottomPageNumber
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.GuitarImage
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.TextDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.fontDimensionResource
import com.microsoft.device.samples.dualscreenexperience.presentation.util.getImageUri
import com.microsoft.device.samples.dualscreenexperience.presentation.util.sizeOrZero

const val SEVENTH_PAGE_CONTENT_ID = "seventhPageContent"
const val SEVENTH_PAGE_BOTTOM_PAGE_NUMBER_ID = "seventhPageBottomPageNumber"
const val SEVENTH_PAGE_FIRST_ROW_ID = "seventhPageFirstRow"
const val SEVENTH_PAGE_SECOND_ROW_ID = "seventhPageSecondRow"

@Composable
fun CatalogSeventhPage(
    modifier: Modifier = Modifier,
    catalogList: List<CatalogItem>,
    isFeatureHorizontal: Boolean = false
) {
    val catalogItem = catalogList[CatalogPage.Page7.ordinal]

    val constraintSet = getMainConstraintSet()
    val seventhPageConstraintSet = getConstraintSetForSeventhPage(isFeatureHorizontal)

    ConstraintLayout(constraintSet) {
        SeventhPageContent(
            modifier
                .padding(
                    start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    end = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    bottom = dimensionResource(id = R.dimen.catalog_margin_normal),
                    top = if (isFeatureHorizontal)
                        dimensionResource(id = R.dimen.catalog_margin_normal) else
                        dimensionResource(id = R.dimen.zero_padding)
                )
                .verticalScroll(rememberScrollState())
                .layoutId(SEVENTH_PAGE_CONTENT_ID),
            seventhPageConstraintSet,
            catalogItem,
            isFeatureHorizontal
        )

        BottomPageNumber(
            modifier = Modifier.layoutId(SEVENTH_PAGE_BOTTOM_PAGE_NUMBER_ID),
            text = stringResource(
                id = R.string.catalog_page_no,
                CatalogPage.Page7.ordinal + 1,
                catalogList.sizeOrZero()
            )
        )
    }
}

private fun getMainConstraintSet() = ConstraintSet {
    val seventhPageRef = createRefFor(SEVENTH_PAGE_CONTENT_ID)
    val bottomPageNumber = createRefFor(SEVENTH_PAGE_BOTTOM_PAGE_NUMBER_ID)

    constrain(seventhPageRef) {
        linkTo(start = parent.start, end = parent.end)
        linkTo(top = parent.top, bottom = bottomPageNumber.top)
    }

    constrain(bottomPageNumber) {
        start.linkTo(parent.start)
        bottom.linkTo(parent.bottom)
    }
}

private fun getConstraintSetForSeventhPage(isFeatureHorizontal: Boolean) = ConstraintSet {
    val firstRowRef = createRefFor(SEVENTH_PAGE_FIRST_ROW_ID)
    val secondRowRef = createRefFor(SEVENTH_PAGE_SECOND_ROW_ID)
    val horizontalGuideline = createGuidelineFromTop(0.5f)

    val topMargin = 20.dp

    constrain(firstRowRef) {
        linkTo(start = parent.start, end = parent.end)
        top.linkTo(parent.top)
        if (isFeatureHorizontal) {
            bottom.linkTo(horizontalGuideline)
        }
    }

    constrain(secondRowRef) {
        linkTo(start = parent.start, end = parent.end)
        if (isFeatureHorizontal) {
            linkTo(top = horizontalGuideline, bottom = parent.bottom, topMargin = topMargin)
        } else {
            top.linkTo(firstRowRef.bottom, margin = topMargin)
        }
    }
}

@Composable
fun SeventhPageContent(
    modifier: Modifier,
    constraintSet: ConstraintSet,
    catalogItem: CatalogItem,
    isFeatureHorizontal: Boolean
) {
    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(top = dimensionResource(id = R.dimen.catalog_top_margin))
                .layoutId(SEVENTH_PAGE_FIRST_ROW_ID),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            GuitarImage(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.catalog_top_margin))
                    .weight(1f)
                    .heightIn(
                        min = dimensionResource(id = R.dimen.catalog_min_image_height),
                        max = dimensionResource(id = R.dimen.catalog_max_image_height)
                    ),
                painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.firstPicture)),
                contentDescription = catalogItem.firstPictureDescription
            )

            TextDescription(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.normal_margin))
                    .weight(1f),
                text = catalogItem.primaryDescription,
                fontSize = if (isFeatureHorizontal)
                    fontDimensionResource(id = R.dimen.text_size_16) else
                    fontDimensionResource(id = R.dimen.text_size_12),
                contentDescription = catalogItem.primaryDescription
            )
        }

        Row(
            modifier = Modifier
                .layoutId(SEVENTH_PAGE_SECOND_ROW_ID),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TextDescription(
                modifier = Modifier.weight(1f),
                text = catalogItem.secondaryDescription ?: "",
                contentDescription = catalogItem.secondaryDescription ?: "",
                fontSize = if (isFeatureHorizontal)
                    fontDimensionResource(id = R.dimen.text_size_16) else
                    fontDimensionResource(id = R.dimen.text_size_12)
            )

            GuitarImage(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(
                        min = dimensionResource(id = R.dimen.catalog_min_image_height),
                        max = dimensionResource(id = R.dimen.catalog_max_image_height)
                    ),
                painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.secondPicture)),
                contentDescription = catalogItem.secondaryDescription
            )
        }
    }
}
