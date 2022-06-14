/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.view

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

const val FIFTH_PAGE_FIRST_TEXT_ID = "fifthPageFirstText"
const val FIFTH_PAGE_FIRST_IMAGE_ID = "fifthPageFirstImage"
const val FIFTH_PAGE_SECOND_IMAGE_ID = "fifthPageSecondImage"
const val FIFTH_PAGE_SECOND_TEXT_ID = "fifthPageSecondText"

@Composable
fun CatalogFifthPage(
    modifier: Modifier = Modifier,
    catalogList: List<CatalogItem>,
    isFeatureHorizontal: Boolean = false,
    isSmallWindowWidth: Boolean = false,
    showTwoPages: Boolean = false
) {
    val pageNumberOrdinal = CatalogPage.Page5.ordinal
    val catalogItem = catalogList[pageNumberOrdinal]
    val fourthPageConstraintSet = getConstraintSetForFifthPage(isFeatureHorizontal)

    PageLayout(
        modifier,
        pageNumberOrdinal + 1,
        catalogList.sizeOrZero()
    ) {
        FifthPageContent(
            modifier = modifier
                .padding(
                    start = dimensionResource(
                        id = if (isSmallWindowWidth && !showTwoPages)
                            R.dimen.catalog_margin_small
                        else
                            R.dimen.catalog_horizontal_margin
                    ),
                    end = dimensionResource(
                        id = if (isSmallWindowWidth && !showTwoPages)
                            R.dimen.catalog_margin_small
                        else
                            R.dimen.catalog_horizontal_margin
                    ),
                    top = if (isFeatureHorizontal)
                        dimensionResource(id = R.dimen.catalog_margin_normal)
                    else
                        dimensionResource(id = R.dimen.zero_padding),
                )
                .verticalScroll(rememberScrollState()),
            constraintSet = fourthPageConstraintSet,
            catalogItem = catalogItem,
            isFeatureHorizontal = isFeatureHorizontal,
            isSmallWindowWidth = isSmallWindowWidth,
            showTwoPages = showTwoPages
        )
    }
}

private fun getConstraintSetForFifthPage(
    isFeatureHorizontal: Boolean
) =
    ConstraintSet {
        val firstTextRef = createRefFor(FIFTH_PAGE_FIRST_TEXT_ID)
        val firstImageRef = createRefFor(FIFTH_PAGE_FIRST_IMAGE_ID)
        val secondImageRef = createRefFor(FIFTH_PAGE_SECOND_IMAGE_ID)
        val secondTextRef = createRefFor(FIFTH_PAGE_SECOND_TEXT_ID)
        val verticalGuideline = createGuidelineFromStart(0.5f)
        val horizontalGuideline = createGuidelineFromTop(0.5f)

        val topMargin = 20.dp
        val verticalMargin = 16.dp

        constrain(firstTextRef) {
            start.linkTo(parent.start)
            top.linkTo(parent.top, topMargin)
        }

        constrain(firstImageRef) {
            start.linkTo(parent.start)
            end.linkTo(verticalGuideline)
            if (isFeatureHorizontal) {
                bottom.linkTo(horizontalGuideline)
                top.linkTo(firstTextRef.bottom)
            } else {
                top.linkTo(firstTextRef.bottom, topMargin)
            }
            height = Dimension.preferredWrapContent
            width = Dimension.preferredWrapContent
        }

        constrain(secondImageRef) {
            if (isFeatureHorizontal) {
                top.linkTo(firstTextRef.bottom)
                bottom.linkTo(horizontalGuideline, verticalMargin)
            } else {
                top.linkTo(firstImageRef.top)
            }
            start.linkTo(verticalGuideline, 8.dp)
            end.linkTo(parent.end)
            height = Dimension.fillToConstraints
        }

        constrain(secondTextRef) {
            if (isFeatureHorizontal) {
                top.linkTo(horizontalGuideline, margin = verticalMargin)
                linkTo(start = parent.start, end = parent.end)
            } else {
                linkTo(start = firstTextRef.start, end = secondImageRef.start)
                top.linkTo(firstImageRef.bottom, margin = verticalMargin)
            }
            width = Dimension.fillToConstraints
        }
    }

@Composable
fun FifthPageContent(
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
            modifier = Modifier.layoutId(FIFTH_PAGE_FIRST_TEXT_ID),
            text = catalogItem.primaryDescription,
            contentDescription = catalogItem.primaryDescription,
            fontSize = if (isFeatureHorizontal && showTwoPages)
                fontDimensionResource(id = R.dimen.text_size_16)
            else
                fontDimensionResource(id = R.dimen.text_size_12)
        )

        RoundedImage(
            modifier = Modifier
                .layoutId(FIFTH_PAGE_FIRST_IMAGE_ID)
                .requiredWidth(
                    if (!showTwoPages && isSmallWindowWidth)
                        dimensionResource(id = R.dimen.catalog_small_screen_min_image_width)
                    else
                        dimensionResource(id = R.dimen.catalog_min_image_width)
                )
                .requiredHeight(
                    if (!showTwoPages && isSmallWindowWidth)
                        dimensionResource(id = R.dimen.catalog_base_min_image_height)
                    else
                        dimensionResource(id = R.dimen.catalog_min_image_height)
                ),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.firstPicture)),
            contentDescription = catalogItem.firstPictureDescription,
            contentScale = ContentScale.FillBounds
        )

        RoundedImage(
            modifier = Modifier
                .layoutId(FIFTH_PAGE_SECOND_IMAGE_ID)
                .padding(
                    bottom = if (!showTwoPages && isSmallWindowWidth)
                        dimensionResource(id = R.dimen.small_margin)
                    else
                        dimensionResource(id = R.dimen.zero_padding)
                )
                .requiredWidth(
                    if (!showTwoPages && isSmallWindowWidth)
                        dimensionResource(id = R.dimen.catalog_small_screen_min_image_width)
                    else
                        dimensionResource(id = R.dimen.catalog_min_image_width)
                )
                .heightIn(
                    min = dimensionResource(id = R.dimen.catalog_medium_image_height),
                    max = dimensionResource(id = R.dimen.catalog_double_max_image_height)
                ),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.secondPicture)),
            contentDescription = catalogItem.secondaryDescription,
            contentScale = ContentScale.FillBounds
        )

        TextDescription(
            modifier = Modifier
                .padding(
                    end = if (!showTwoPages && isSmallWindowWidth)
                        dimensionResource(id = R.dimen.small_margin)
                    else
                        dimensionResource(id = R.dimen.catalog_horizontal_margin)
                )
                .layoutId(FIFTH_PAGE_SECOND_TEXT_ID),
            text = catalogItem.secondaryDescription ?: "",
            contentDescription = catalogItem.secondaryDescription ?: "",
            fontSize = if (isFeatureHorizontal && showTwoPages)
                fontDimensionResource(id = R.dimen.text_size_16)
            else
                fontDimensionResource(id = R.dimen.text_size_12)
        )
    }
}
