/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
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

const val SECOND_PAGE_TITLE_TEXT_ID = "secondPageTitle"
const val SECOND_PAGE_FIRST_ROW_ID = "secondPageFirstRow"
const val SECOND_PAGE_SECOND_TEXT_ID = "secondPageSecondText"
const val SECOND_PAGE_THIRD_TEXT_ID = "secondPageThirdText"
const val SECOND_PAGE_FIRST_IMAGE_ID = "secondPageFirstImage"
const val SECOND_PAGE_FIRST_ROW_IMAGE_ID = "secondPageFirstRowImage"
const val SECOND_PAGE_SECOND_ROW_IMAGE_ID = "secondPageSecondRowImage"

const val HORIZONTAL_GUIDELINE_FRACTION = 0.5f

@Composable
fun CatalogSecondPage(
    modifier: Modifier = Modifier,
    catalogList: List<CatalogItem>,
    isFeatureHorizontal: Boolean = false,
    showTwoPages: Boolean = false,
    isSmallWindowWidth: Boolean = false
) {
    val pageNumberOrdinal = CatalogPage.Page2.ordinal
    val catalogItem = catalogList[pageNumberOrdinal]

    val secondPageConstraintSet = if (isSmallWindowWidth)
        getConstraintSetForSmallWindowWidth() else
        getConstraintSetForSecondPage(isFeatureHorizontal)

    PageLayout(
        modifier,
        pageNumberOrdinal + 1,
        catalogList.sizeOrZero()
    ) {
        SecondPageContent(
            modifier = modifier
                .padding(
                    top = if (isFeatureHorizontal)
                        dimensionResource(id = R.dimen.catalog_margin_normal) else
                        dimensionResource(id = R.dimen.zero_padding)
                )
                .verticalScroll(rememberScrollState()),
            constraintSet = secondPageConstraintSet,
            catalogItem = catalogItem,
            isFeatureHorizontal = isFeatureHorizontal,
            isSmallWindowWidth = isSmallWindowWidth,
            showTwoPages = showTwoPages
        )
    }
}

@Composable
private fun getConstraintSetForSecondPage(isFeatureHorizontal: Boolean) = ConstraintSet {
    val titleRef = createRefFor(SECOND_PAGE_TITLE_TEXT_ID)
    val firstRowRef = createRefFor(SECOND_PAGE_FIRST_ROW_ID)
    val thirdTextRef = createRefFor(SECOND_PAGE_THIRD_TEXT_ID)
    val firstRowImageRef = createRefFor(SECOND_PAGE_FIRST_ROW_IMAGE_ID)
    val secondRowImageRef = createRefFor(SECOND_PAGE_SECOND_ROW_IMAGE_ID)
    val horizontalGuideline = createGuidelineFromTop(HORIZONTAL_GUIDELINE_FRACTION)

    val horizontalMargin = 36.dp
    val topMargin = 20.dp
    val smallMargin = 8.dp

    constrain(titleRef) {
        top.linkTo(parent.top, topMargin)
        start.linkTo(parent.start, horizontalMargin)
    }

    constrain(firstRowRef) {
        linkTo(start = parent.start, end = parent.end)
        top.linkTo(titleRef.bottom, smallMargin)
        if (isFeatureHorizontal) {
            bottom.linkTo(horizontalGuideline)
        }
    }

    constrain(thirdTextRef) {
        linkTo(start = parent.start, end = parent.end)
        if (isFeatureHorizontal) {
            top.linkTo(horizontalGuideline)
        } else {
            top.linkTo(firstRowRef.bottom)
        }
    }

    constrain(firstRowImageRef) {
        top.linkTo(thirdTextRef.bottom, topMargin)
        start.linkTo(firstRowRef.start)
    }

    constrain(secondRowImageRef) {
        start.linkTo(firstRowImageRef.start)
        top.linkTo(firstRowImageRef.top)
        end.linkTo(firstRowRef.end)
    }

    createHorizontalChain(
        firstRowImageRef,
        secondRowImageRef,
        chainStyle = ChainStyle.SpreadInside
    )
}

@Composable
private fun getConstraintSetForSmallWindowWidth() = ConstraintSet {
    val titleRef = createRefFor(SECOND_PAGE_TITLE_TEXT_ID)
    val firstImageRef = createRefFor(SECOND_PAGE_FIRST_IMAGE_ID)
    val secondTextRef = createRefFor(SECOND_PAGE_SECOND_TEXT_ID)
    val thirdTextRef = createRefFor(SECOND_PAGE_THIRD_TEXT_ID)
    val firstRowImageRef = createRefFor(SECOND_PAGE_FIRST_ROW_IMAGE_ID)
    val secondRowImageRef = createRefFor(SECOND_PAGE_SECOND_ROW_IMAGE_ID)

    val horizontalMargin = 36.dp
    val topMargin = 20.dp
    val smallMargin = 8.dp

    constrain(titleRef) {
        top.linkTo(parent.top, topMargin)
        start.linkTo(parent.start, horizontalMargin)
    }

    constrain(secondTextRef) {
        linkTo(
            start = parent.start,
            end = parent.end,
            startMargin = horizontalMargin,
            endMargin = horizontalMargin
        )
        top.linkTo(titleRef.bottom, smallMargin)
    }

    constrain(firstImageRef) {
        linkTo(start = parent.start, end = parent.end)
        top.linkTo(secondTextRef.bottom, smallMargin)
        height = Dimension.fillToConstraints
    }

    constrain(thirdTextRef) {
        linkTo(start = parent.start, end = parent.end)
        top.linkTo(firstImageRef.bottom)
    }

    constrain(firstRowImageRef) {
        top.linkTo(thirdTextRef.bottom, smallMargin)
        start.linkTo(secondTextRef.start, horizontalMargin)
    }

    constrain(secondRowImageRef) {
        start.linkTo(firstRowImageRef.start)
        top.linkTo(firstRowImageRef.top)
        end.linkTo(secondTextRef.end, horizontalMargin)
    }

    createHorizontalChain(
        firstRowImageRef,
        secondRowImageRef,
        chainStyle = ChainStyle.Spread
    )
}

@Composable
private fun SecondPageContent(
    modifier: Modifier,
    constraintSet: ConstraintSet,
    catalogItem: CatalogItem,
    isFeatureHorizontal: Boolean,
    isSmallWindowWidth: Boolean,
    showTwoPages: Boolean
) {
    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = modifier
    ) {
        TextDescription(
            modifier = Modifier.layoutId(SECOND_PAGE_TITLE_TEXT_ID),
            text = catalogItem.primaryDescription,
            fontSize = if (isFeatureHorizontal)
                fontDimensionResource(id = R.dimen.text_size_20) else
                fontDimensionResource(id = R.dimen.text_size_16),
            contentDescription = catalogItem.primaryDescription
        )

        if (isSmallWindowWidth && !showTwoPages) {
            TextDescription(
                modifier = Modifier
                    .layoutId(SECOND_PAGE_SECOND_TEXT_ID)
                    .padding(horizontal = dimensionResource(id = R.dimen.catalog_horizontal_margin)),

                text = catalogItem.secondaryDescription ?: "",
                fontSize = if (isFeatureHorizontal)
                    fontDimensionResource(id = R.dimen.text_size_16) else
                    fontDimensionResource(id = R.dimen.text_size_12),
                contentDescription = catalogItem.secondaryDescription ?: ""
            )

            RoundedImage(
                modifier = Modifier
                    .layoutId(SECOND_PAGE_FIRST_IMAGE_ID)
                    .requiredWidth(dimensionResource(id = R.dimen.catalog_small_screen_min_image_width))
                    .requiredHeight(dimensionResource(id = R.dimen.catalog_min_image_height)),
                painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.firstPicture)),
                contentDescription = catalogItem.firstPictureDescription,
                contentScale = ContentScale.FillHeight
            )
        } else {
            Row(
                modifier = Modifier
                    .layoutId(SECOND_PAGE_FIRST_ROW_ID)
                    .padding(top = dimensionResource(id = R.dimen.catalog_margin_small)),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                RoundedImage(
                    modifier = Modifier
                        .padding(start = dimensionResource(id = R.dimen.catalog_image_margin_start))
                        .requiredWidth(dimensionResource(id = R.dimen.catalog_min_image_width))
                        .requiredHeight(dimensionResource(id = R.dimen.catalog_min_image_height)),
                    painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.firstPicture)),
                    contentDescription = catalogItem.firstPictureDescription
                )

                TextDescription(
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.small_margin),
                        start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                        end = dimensionResource(id = R.dimen.catalog_horizontal_margin)
                    ),
                    text = catalogItem.secondaryDescription ?: "",
                    fontSize = if (isFeatureHorizontal)
                        fontDimensionResource(id = R.dimen.text_size_16) else
                        fontDimensionResource(id = R.dimen.text_size_12),
                    contentDescription = catalogItem.secondaryDescription ?: ""
                )
            }
        }

        TextDescription(
            modifier = Modifier
                .layoutId(SECOND_PAGE_THIRD_TEXT_ID)
                .padding(
                    start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    end = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    top = dimensionResource(id = R.dimen.catalog_margin_large)
                ),
            text = catalogItem.thirdDescription ?: "",
            fontSize = if (isFeatureHorizontal)
                fontDimensionResource(id = R.dimen.text_size_16) else
                fontDimensionResource(id = R.dimen.text_size_12),
            contentDescription = catalogItem.thirdDescription ?: ""
        )

        RoundedImage(
            modifier = Modifier
                .layoutId(SECOND_PAGE_FIRST_ROW_IMAGE_ID)
                .padding(
                    start = if (isSmallWindowWidth && !showTwoPages)
                        dimensionResource(id = R.dimen.zero_padding) else
                        dimensionResource(id = R.dimen.catalog_image_margin_start)
                )
                .requiredWidth(
                    if (isSmallWindowWidth && !showTwoPages)
                        dimensionResource(id = R.dimen.catalog_small_screen_min_image_width) else
                        dimensionResource(id = R.dimen.catalog_min_image_width)
                )
                .requiredHeight(dimensionResource(id = R.dimen.catalog_min_image_height)),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.secondPicture)),
            contentDescription = catalogItem.firstPictureDescription
        )

        RoundedImage(
            modifier = Modifier
                .layoutId(SECOND_PAGE_SECOND_ROW_IMAGE_ID)
                .padding(
                    end = if (isSmallWindowWidth && !showTwoPages)
                        dimensionResource(id = R.dimen.zero_padding) else
                        dimensionResource(id = R.dimen.catalog_image_margin_end)
                )
                .requiredWidth(
                    if (isSmallWindowWidth && !showTwoPages)
                        dimensionResource(id = R.dimen.catalog_small_screen_min_image_width) else
                        dimensionResource(id = R.dimen.catalog_min_image_width)
                )
                .requiredHeight(dimensionResource(id = R.dimen.catalog_min_image_height)),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.thirdPicture)),
            contentDescription = catalogItem.firstPictureDescription
        )
    }
}
