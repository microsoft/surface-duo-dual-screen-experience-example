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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.rememberAsyncImagePainter
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.PageLayout
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.RoundedImage
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.TextDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.fontDimensionResource
import com.microsoft.device.samples.dualscreenexperience.presentation.util.getImageUri
import com.microsoft.device.samples.dualscreenexperience.presentation.util.sizeOrZero

const val SEVENTH_PAGE_FIRST_ROW_ID = "seventhPageFirstRow"
const val SEVENTH_PAGE_SECOND_ROW_ID = "seventhPageSecondRow"
const val SEVENTH_PAGE_FIRST_IMAGE_ID = "seventhPageFirstImage"
const val SEVENTH_PAGE_SECOND_IMAGE_ID = "seventhPageSecondImage"
const val SEVENTH_PAGE_FIRST_TEXT_ID = "seventhPageFirstText"
const val SEVENTH_PAGE_SECOND_TEXT_ID = "seventhPageSecondText"

@Composable
fun CatalogSeventhPage(
    modifier: Modifier = Modifier,
    catalogList: List<CatalogItem>,
    isFeatureHorizontal: Boolean = false,
    isSmallWindowWidth: Boolean = false,
    showTwoPages: Boolean = false
) {
    val pageNumberOrdinal = CatalogPage.Page7.ordinal
    val catalogItem = catalogList[pageNumberOrdinal]

    PageLayout(
        modifier,
        pageNumberOrdinal + 1,
        catalogList.sizeOrZero()
    ) {
        if (isSmallWindowWidth && !showTwoPages) {
            SeventhPageContentSmallWidth(
                modifier
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                        end = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                        top = if (isFeatureHorizontal)
                            dimensionResource(id = R.dimen.catalog_margin_normal)
                        else
                            dimensionResource(id = R.dimen.zero_padding)
                    ),
                getConstraintSetForSmallWindowWidth(),
                catalogItem,
                isFeatureHorizontal
            )
        } else {
            SeventhPageContent(
                modifier
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                        end = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                        top = if (isFeatureHorizontal)
                            dimensionResource(id = R.dimen.catalog_margin_normal)
                        else
                            dimensionResource(id = R.dimen.zero_padding)
                    ),
                getConstraintSetForSeventhPage(isFeatureHorizontal),
                catalogItem,
                isFeatureHorizontal
            )
        }
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
        top.linkTo(if (isFeatureHorizontal) horizontalGuideline else firstRowRef.bottom, topMargin)
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

            RoundedImage(
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
                    fontDimensionResource(id = R.dimen.text_size_16)
                else
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
                    fontDimensionResource(id = R.dimen.text_size_16)
                else
                    fontDimensionResource(id = R.dimen.text_size_12)
            )

            RoundedImage(
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

@Composable
private fun getConstraintSetForSmallWindowWidth() = ConstraintSet {
    val firstImageRef = createRefFor(SEVENTH_PAGE_FIRST_IMAGE_ID)
    val firstTextRef = createRefFor(SEVENTH_PAGE_FIRST_TEXT_ID)
    val secondImageRef = createRefFor(SEVENTH_PAGE_SECOND_IMAGE_ID)
    val secondTextRef = createRefFor(SEVENTH_PAGE_SECOND_TEXT_ID)

    val topMargin = 20.dp

    constrain(firstImageRef) {
        top.linkTo(parent.top, topMargin)
        linkTo(start = parent.start, end = parent.end)
    }

    constrain(firstTextRef) {
        top.linkTo(firstImageRef.bottom, topMargin)
        linkTo(start = parent.start, end = parent.end)
    }

    constrain(secondImageRef) {
        top.linkTo(firstTextRef.bottom, topMargin)
        linkTo(start = parent.start, end = parent.end)
    }

    constrain(secondTextRef) {
        top.linkTo(secondImageRef.bottom, topMargin)
        linkTo(start = parent.start, end = parent.end)
    }
}

@Composable
fun SeventhPageContentSmallWidth(
    modifier: Modifier,
    constraintSet: ConstraintSet,
    catalogItem: CatalogItem,
    isFeatureHorizontal: Boolean
) {
    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = modifier
    ) {
        RoundedImage(
            modifier = Modifier
                .layoutId(SEVENTH_PAGE_FIRST_IMAGE_ID)
                .requiredWidth(dimensionResource(id = R.dimen.catalog_small_screen_min_image_width))
                .requiredHeight(dimensionResource(id = R.dimen.catalog_min_image_height))
                .heightIn(
                    min = dimensionResource(id = R.dimen.catalog_min_image_height),
                    max = dimensionResource(id = R.dimen.catalog_max_image_height)
                ),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.firstPicture)),
            contentDescription = catalogItem.firstPictureDescription,
            contentScale = ContentScale.FillBounds
        )

        TextDescription(
            modifier = Modifier.layoutId(SEVENTH_PAGE_FIRST_TEXT_ID),
            text = catalogItem.primaryDescription,
            fontSize = if (isFeatureHorizontal)
                fontDimensionResource(id = R.dimen.text_size_16)
            else
                fontDimensionResource(id = R.dimen.text_size_12),
            contentDescription = catalogItem.primaryDescription
        )

        RoundedImage(
            modifier = Modifier
                .layoutId(SEVENTH_PAGE_SECOND_IMAGE_ID)
                .requiredWidth(dimensionResource(id = R.dimen.catalog_small_screen_min_image_width))
                .requiredHeight(dimensionResource(id = R.dimen.catalog_min_image_height))
                .heightIn(
                    min = dimensionResource(id = R.dimen.catalog_min_image_height),
                    max = dimensionResource(id = R.dimen.catalog_max_image_height)
                ),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.secondPicture)),
            contentDescription = catalogItem.secondaryDescription,
            contentScale = ContentScale.FillBounds
        )

        TextDescription(
            modifier = Modifier.layoutId(SEVENTH_PAGE_SECOND_TEXT_ID),
            text = catalogItem.secondaryDescription ?: "",
            contentDescription = catalogItem.secondaryDescription ?: "",
            fontSize = if (isFeatureHorizontal)
                fontDimensionResource(id = R.dimen.text_size_16)
            else
                fontDimensionResource(id = R.dimen.text_size_12)
        )
    }
}
