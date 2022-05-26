/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.view

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
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

const val FIFTH_PAGE_CONTENT_ID = "fifthPageContent"
const val FIFTH_PAGE_BOTTOM_PAGE_NUMBER_ID = "fifthPageBottomPageNumber"
const val FIFTH_PAGE_FIRST_TEXT_ID = "fifthPageFirstText"
const val FIFTH_PAGE_FIRST_IMAGE_ID = "fifthPageFirstImage"
const val FIFTH_PAGE_SECOND_IMAGE_ID = "fifthPageSecondImage"
const val FIFTH_PAGE_SECOND_TEXT_ID = "fifthPageSecondText"

@Composable
fun CatalogFifthPage(
    modifier: Modifier = Modifier,
    catalogList: List<CatalogItem>,
    isFeatureHorizontal: Boolean = false,
    isDualScreen: Boolean
) {
    val catalogItem = catalogList[CatalogPage.Page5.ordinal]

    val constraintSet = getMainConstraintSet()
    val fourthPageConstraintSet =
        getConstraintSetForFifthPage(isFeatureHorizontal, isDualScreen)

    ConstraintLayout(constraintSet) {
        FifthPageContent(
            modifier
                .padding(
                    start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    end = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    bottom = dimensionResource(id = R.dimen.catalog_margin_normal),
                    top = if (isFeatureHorizontal) dimensionResource(id = R.dimen.catalog_margin_normal) else
                        dimensionResource(id = R.dimen.zero_padding),

                )
                .verticalScroll(rememberScrollState())
                .layoutId(FOURTH_PAGE_CONTENT_ID),
            fourthPageConstraintSet,
            catalogItem,
            isFeatureHorizontal
        )

        BottomPageNumber(
            modifier = Modifier.layoutId(FIFTH_PAGE_BOTTOM_PAGE_NUMBER_ID),
            text = stringResource(
                id = R.string.catalog_page_no,
                CatalogPage.Page5.ordinal + 1,
                catalogList.sizeOrZero()
            )
        )
    }
}

private fun getMainConstraintSet() = ConstraintSet {
    val fifthPageRef = createRefFor(FIFTH_PAGE_CONTENT_ID)
    val bottomPageNumber = createRefFor(FIFTH_PAGE_BOTTOM_PAGE_NUMBER_ID)

    constrain(fifthPageRef) {
        linkTo(start = parent.start, end = parent.end)
        linkTo(top = parent.top, bottom = bottomPageNumber.top)
    }

    constrain(bottomPageNumber) {
        start.linkTo(parent.start)
        bottom.linkTo(parent.bottom)
    }
}

private fun getConstraintSetForFifthPage(
    isFeatureHorizontal: Boolean,
    isDualScreen: Boolean
) =
    ConstraintSet {
        val firstTextRef = createRefFor(FIFTH_PAGE_FIRST_TEXT_ID)
        val firstImageRef = createRefFor(FIFTH_PAGE_FIRST_IMAGE_ID)
        val secondImageRef = createRefFor(FIFTH_PAGE_SECOND_IMAGE_ID)
        val secondTextRef = createRefFor(FIFTH_PAGE_SECOND_TEXT_ID)
        val verticalGuideline = createGuidelineFromStart(0.5f)
        val horizontalGuideline = createGuidelineFromTop(0.5f)

        constrain(firstTextRef) {
            start.linkTo(parent.start)
            top.linkTo(parent.top, 20.dp)
        }

        constrain(firstImageRef) {
            start.linkTo(parent.start)
            end.linkTo(verticalGuideline)
            top.linkTo(firstTextRef.bottom, 20.dp)
            if (isFeatureHorizontal) {
                bottom.linkTo(horizontalGuideline)
            }
        }

        constrain(secondImageRef) {
            if (isFeatureHorizontal) {
                top.linkTo(parent.top)
                bottom.linkTo(horizontalGuideline)
            } else {
                top.linkTo(firstImageRef.top)
            }
            start.linkTo(verticalGuideline)
            end.linkTo(parent.end)
        }

        constrain(secondTextRef) {
            if (isFeatureHorizontal) {
                top.linkTo(horizontalGuideline, margin = 16.dp)
                linkTo(start = parent.start, end = parent.end)
            } else {
                linkTo(start = firstTextRef.start, end = secondImageRef.start)
                top.linkTo(firstImageRef.bottom, margin = if (isDualScreen) 8.dp else 16.dp)
            }
            width = Dimension.fillToConstraints
        }
    }

@Composable
fun FifthPageContent(
    modifier: Modifier,
    constraintSet: ConstraintSet,
    catalogItem: CatalogItem,
    isFeatureHorizontal: Boolean
) {
    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = modifier
    ) {

        TextDescription(
            modifier = Modifier
                .layoutId(FIFTH_PAGE_FIRST_TEXT_ID),
            text = catalogItem.primaryDescription,
            contentDescription = catalogItem.primaryDescription,
            fontSize = if (isFeatureHorizontal)
                fontDimensionResource(id = R.dimen.text_size_16) else
                fontDimensionResource(id = R.dimen.text_size_12)
        )

        GuitarImage(
            modifier = Modifier
                .layoutId(FIFTH_PAGE_FIRST_IMAGE_ID)
                .heightIn(
                    min = dimensionResource(id = R.dimen.catalog_min_image_height),
                    max = dimensionResource(id = R.dimen.catalog_max_image_height)
                ),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.firstPicture)),
            contentDescription = catalogItem.firstPictureDescription,
            contentScale = ContentScale.FillBounds
        )

        GuitarImage(
            modifier = Modifier
                .heightIn(
                    min = dimensionResource(id = R.dimen.catalog_min_image_height),
                    max = dimensionResource(id = R.dimen.catalog_double_max_image_height)
                )
                .layoutId(FIFTH_PAGE_SECOND_IMAGE_ID),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.secondPicture)),
            contentDescription = catalogItem.secondaryDescription,
            contentScale = ContentScale.FillBounds
        )

        TextDescription(
            modifier = Modifier
                .padding(end = dimensionResource(id = R.dimen.catalog_horizontal_margin))
                .layoutId(FIFTH_PAGE_SECOND_TEXT_ID),
            text = catalogItem.secondaryDescription ?: "",
            contentDescription = catalogItem.secondaryDescription ?: "",
            fontSize = if (isFeatureHorizontal)
                fontDimensionResource(id = R.dimen.text_size_16) else
                fontDimensionResource(id = R.dimen.text_size_12)
        )
    }
}
