/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
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

const val THIRD_PAGE_FIRST_ROW_ID = "thirdPageFirstRow"
const val THIRD_PAGE_SECOND_ROW_ID = "thirdPageSecondRow"
const val THIRD_PAGE_FIRST_IMAGE_ID = "thirdPageFirstImage"
const val THIRD_PAGE_FIRST_TEXT_ID = "thirdPageFirstText"
const val THIRD_PAGE_SECOND_TEXT_ID = "thirdPageSecondText"
const val THIRD_PAGE_THIRD_TEXT_ID = "thirdPageThirdText"
const val THIRD_PAGE_SECOND_IMAGE_ID = "thirdPageSecondImage"

const val ROW_WEIGHT = 1f

@Composable
fun CatalogThirdPage(
    modifier: Modifier = Modifier,
    catalogList: List<CatalogItem>,
    isFeatureHorizontal: Boolean = false,
    isSinglePortrait: Boolean,
    showTwoPages: Boolean = false,
    isSmallWindowWidth: Boolean = false,
    isFoldStateHalfOpened: Boolean = false
) {
    val pageNumberOrdinal = CatalogPage.Page3.ordinal
    val catalogItem = catalogList[pageNumberOrdinal]

    PageLayout(
        modifier,
        pageNumberOrdinal + 1,
        catalogList.sizeOrZero()
    ) {
        val contentModifier = modifier
            .padding(top = dimensionResource(id = R.dimen.catalog_margin_normal))
            .verticalScroll(rememberScrollState())
        if (isSmallWindowWidth) {
            ThirdPageSmallWindowWidthContent(
                modifier = contentModifier,
                constraintSet = getConstraintSetForSmallWindowWidth(),
                catalogItem = catalogItem
            )
        } else {
            ThirdPageContent(
                modifier = contentModifier,
                constraintSet = getConstraintSetForThirdPage(isFeatureHorizontal),
                catalogItem = catalogItem,
                isFeatureHorizontal = isFeatureHorizontal,
                isSinglePortrait = isSinglePortrait,
                showTwoPages = showTwoPages,
                isFoldStateHalfOpened = isFoldStateHalfOpened
            )
        }
    }
}

private fun getConstraintSetForThirdPage(isFeatureHorizontal: Boolean) = ConstraintSet {
    val firstRowRef = createRefFor(THIRD_PAGE_FIRST_ROW_ID)
    val secondRowRef = createRefFor(THIRD_PAGE_SECOND_ROW_ID)
    val horizontalGuideline = createGuidelineFromTop(0.5f)

    val topMargin = 20.dp
    val smallMargin = 8.dp

    constrain(firstRowRef) {
        linkTo(
            start = parent.start,
            startMargin = smallMargin,
            end = parent.end,
            endMargin = smallMargin
        )
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
private fun getConstraintSetForSmallWindowWidth() = ConstraintSet {
    val firstImageRef = createRefFor(THIRD_PAGE_FIRST_IMAGE_ID)
    val firstTextRef = createRefFor(THIRD_PAGE_FIRST_TEXT_ID)
    val secondImageRef = createRefFor(THIRD_PAGE_SECOND_IMAGE_ID)
    val secondTextRef = createRefFor(THIRD_PAGE_SECOND_TEXT_ID)
    val thirdTextRef = createRefFor(THIRD_PAGE_THIRD_TEXT_ID)

    val horizontalMargin = 36.dp
    val topMargin = 20.dp
    val smallMargin = 8.dp

    constrain(firstImageRef) {
        top.linkTo(parent.top, topMargin)
        linkTo(start = parent.start, end = parent.end)
    }

    constrain(firstTextRef) {
        top.linkTo(firstImageRef.bottom, topMargin)
        linkTo(
            start = parent.start,
            end = parent.end,
            startMargin = horizontalMargin,
            endMargin = horizontalMargin
        )
    }

    constrain(secondImageRef) {
        top.linkTo(firstTextRef.bottom, smallMargin)
        linkTo(start = parent.start, end = parent.end)
    }

    constrain(secondTextRef) {
        top.linkTo(secondImageRef.bottom, topMargin)
        start.linkTo(parent.start, horizontalMargin)
    }

    constrain(thirdTextRef) {
        top.linkTo(secondTextRef.bottom)
        linkTo(
            start = parent.start,
            end = parent.end,
            startMargin = horizontalMargin,
            endMargin = horizontalMargin
        )
    }
}

@Composable
fun ThirdPageContent(
    modifier: Modifier,
    constraintSet: ConstraintSet,
    catalogItem: CatalogItem,
    isFeatureHorizontal: Boolean,
    isSinglePortrait: Boolean,
    showTwoPages: Boolean,
    isFoldStateHalfOpened: Boolean
) {
    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.catalog_top_margin),
                    start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    end = dimensionResource(id = R.dimen.normal_margin)
                )
                .layoutId(THIRD_PAGE_FIRST_ROW_ID),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            RoundedImage(
                modifier = Modifier
                    .weight(ROW_WEIGHT)
                    .requiredWidth(
                        if ((isFeatureHorizontal && !showTwoPages) ||
                            isSinglePortrait || (isFoldStateHalfOpened && !showTwoPages)
                        )
                            dimensionResource(id = R.dimen.catalog_max_image_height) else
                            dimensionResource(id = R.dimen.catalog_min_image_width)
                    )
                    .requiredHeight(
                        if ((isFeatureHorizontal && !showTwoPages) ||
                            isSinglePortrait || (isFoldStateHalfOpened && !showTwoPages)
                        )
                            dimensionResource(id = R.dimen.catalog_medium_image_height) else
                            dimensionResource(id = R.dimen.catalog_min_image_height)
                    ),
                painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.firstPicture)),
                contentDescription = catalogItem.firstPictureDescription,
                contentScale = ContentScale.FillBounds
            )

            TextDescription(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                        end = dimensionResource(id = R.dimen.normal_margin)
                    )
                    .weight(ROW_WEIGHT),
                text = catalogItem.primaryDescription,
                fontSize = if (isFeatureHorizontal)
                    fontDimensionResource(id = R.dimen.text_size_16) else
                    fontDimensionResource(id = R.dimen.text_size_12),
                contentDescription = catalogItem.primaryDescription
            )
        }

        Row(
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.normal_margin))
                .layoutId(THIRD_PAGE_SECOND_ROW_ID),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.catalog_horizontal_margin))
                    .weight(ROW_WEIGHT)
            ) {
                TextDescription(
                    text = catalogItem.secondaryDescription ?: "",
                    contentDescription = catalogItem.secondaryDescription ?: "",
                    fontSize = if (isFeatureHorizontal)
                        fontDimensionResource(id = R.dimen.text_size_20) else
                        fontDimensionResource(id = R.dimen.text_size_16)
                )
                TextDescription(
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.catalog_top_margin)
                    ),
                    text = catalogItem.thirdDescription ?: "",
                    contentDescription = catalogItem.thirdDescription ?: "",
                    fontSize = if (isFeatureHorizontal)
                        fontDimensionResource(id = R.dimen.text_size_16) else
                        fontDimensionResource(id = R.dimen.text_size_12)
                )
            }

            RoundedImage(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.catalog_top_margin))
                    .weight(ROW_WEIGHT)
                    .requiredWidth(
                        if ((isFeatureHorizontal && !showTwoPages) ||
                            isSinglePortrait || (isFoldStateHalfOpened && !showTwoPages)
                        )
                            dimensionResource(id = R.dimen.catalog_max_image_height) else
                            dimensionResource(id = R.dimen.catalog_min_image_width)
                    )
                    .requiredHeight(
                        if ((isFeatureHorizontal && !showTwoPages) ||
                            isSinglePortrait || (isFoldStateHalfOpened && !showTwoPages)
                        )
                            dimensionResource(id = R.dimen.catalog_medium_image_height) else
                            dimensionResource(id = R.dimen.catalog_min_image_height)
                    )
                    .heightIn(
                        min = dimensionResource(id = R.dimen.catalog_min_image_height),
                        max = dimensionResource(id = R.dimen.catalog_max_image_height)
                    ),
                painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.secondPicture)),
                contentDescription = catalogItem.secondaryDescription,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
fun ThirdPageSmallWindowWidthContent(
    modifier: Modifier,
    constraintSet: ConstraintSet,
    catalogItem: CatalogItem
) {
    ConstraintLayout(constraintSet, modifier = modifier) {
        RoundedImage(
            modifier = Modifier
                .layoutId(THIRD_PAGE_FIRST_IMAGE_ID)
                .requiredWidth(dimensionResource(id = R.dimen.catalog_small_screen_min_image_width))
                .requiredHeight(dimensionResource(id = R.dimen.catalog_min_image_height)),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.firstPicture)),
            contentDescription = catalogItem.firstPictureDescription,
            contentScale = ContentScale.FillBounds
        )

        TextDescription(
            modifier = Modifier
                .layoutId(THIRD_PAGE_FIRST_TEXT_ID)
                .padding(
                    start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    end = dimensionResource(id = R.dimen.normal_margin)
                ),
            text = catalogItem.primaryDescription,
            fontSize =
                fontDimensionResource(id = R.dimen.text_size_12),
            contentDescription = catalogItem.primaryDescription
        )

        RoundedImage(
            modifier = Modifier
                .layoutId(THIRD_PAGE_SECOND_IMAGE_ID)
                .padding(top = dimensionResource(id = R.dimen.catalog_top_margin))
                .requiredWidth(dimensionResource(id = R.dimen.catalog_small_screen_min_image_width))
                .requiredHeight(dimensionResource(id = R.dimen.catalog_min_image_height)),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.secondPicture)),
            contentDescription = catalogItem.secondaryDescription,
            contentScale = ContentScale.FillBounds
        )

        TextDescription(
            modifier = Modifier.layoutId(THIRD_PAGE_SECOND_TEXT_ID),
            text = catalogItem.secondaryDescription ?: "",
            contentDescription = catalogItem.secondaryDescription ?: "",
            fontSize = fontDimensionResource(id = R.dimen.text_size_16)
        )

        TextDescription(
            modifier = Modifier
                .layoutId(THIRD_PAGE_THIRD_TEXT_ID)
                .padding(
                    top = dimensionResource(id = R.dimen.catalog_top_margin),
                    start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    end = dimensionResource(id = R.dimen.normal_margin)
                ),
            text = catalogItem.thirdDescription ?: "",
            contentDescription = catalogItem.thirdDescription ?: "",
            fontSize = fontDimensionResource(id = R.dimen.text_size_12)
        )
    }
}
