/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.compose.rememberAsyncImagePainter
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.BottomPageNumber
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.TextDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.fontDimensionResource
import com.microsoft.device.samples.dualscreenexperience.presentation.util.getImageUri
import com.microsoft.device.samples.dualscreenexperience.presentation.util.sizeOrZero

const val SECOND_PAGE_TITLE_TEXT_ID = "secondPageTitle"
const val SECOND_PAGE_CONTENT_ID = "secondPageContent"
const val SECOND_PAGE_BOTTOM_PAGE_NUMBER_ID = "secondPageBottomPageNumber"
const val SECOND_PAGE_FIRST_ROW_ID = "secondPageFirstRow"
const val SECOND_PAGE_THIRD_TEXT_ID = "secondPageThirdText"
const val SECOND_PAGE_FIRST_ROW_IMAGE_ID = "secondPageFirstRowImage"
const val SECOND_PAGE_SECOND_ROW_IMAGE_ID = "secondPageSecondRowImage"

const val HORIZONTAL_GUIDELINE_FRACTION = 0.5f

@Composable
fun CatalogSecondPage(
    modifier: Modifier = Modifier,
    catalogList: List<CatalogItem>,
    isFeatureHorizontal: Boolean = false
) {
    val catalogItem = catalogList[CatalogPage.Page2.ordinal]

    val constraintSet = getMainConstraintSet()
    val secondPageConstraintSet = getConstraintSetForSecondPage(isFeatureHorizontal)

    ConstraintLayout(constraintSet) {
        SecondPageContent(
            modifier
                .padding(
                    bottom = dimensionResource(id = R.dimen.catalog_margin_normal),
                    top = if (isFeatureHorizontal)
                        dimensionResource(id = R.dimen.catalog_margin_normal) else
                        dimensionResource(id = R.dimen.zero_padding)
                )
                .verticalScroll(rememberScrollState())
                .layoutId(SECOND_PAGE_CONTENT_ID),
            secondPageConstraintSet,
            catalogItem,
            isFeatureHorizontal
        )

        BottomPageNumber(
            modifier = Modifier.layoutId(SECOND_PAGE_BOTTOM_PAGE_NUMBER_ID),
            text = stringResource(
                id = R.string.catalog_page_no,
                CatalogPage.Page2.ordinal + 1,
                catalogList.sizeOrZero()
            )
        )
    }
}

@Composable
private fun getMainConstraintSet() = ConstraintSet {
    val secondPageRef = createRefFor(SECOND_PAGE_CONTENT_ID)
    val bottomPageNumber = createRefFor(SECOND_PAGE_BOTTOM_PAGE_NUMBER_ID)

    constrain(secondPageRef) {
        linkTo(start = parent.start, end = parent.end)
        linkTo(top = parent.top, bottom = bottomPageNumber.top)
    }

    constrain(bottomPageNumber) {
        start.linkTo(parent.start)
        bottom.linkTo(parent.bottom)
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

    constrain(titleRef) {
        top.linkTo(parent.top, 20.dp)
        start.linkTo(parent.start, 36.dp)
    }

    constrain(firstRowRef) {
        linkTo(start = parent.start, end = parent.end)
        top.linkTo(titleRef.bottom, 8.dp)
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
        top.linkTo(thirdTextRef.bottom, 16.dp)
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
private fun SecondPageContent(
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
            modifier = Modifier.layoutId(SECOND_PAGE_TITLE_TEXT_ID),
            text = catalogItem.primaryDescription,
            fontSize = if (isFeatureHorizontal)
                fontDimensionResource(id = R.dimen.text_size_20) else
                fontDimensionResource(id = R.dimen.text_size_16),
            contentDescription = catalogItem.primaryDescription
        )

        Row(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.catalog_margin_small))
                .layoutId(SECOND_PAGE_FIRST_ROW_ID),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.catalog_image_margin_start))
                    .clip(MaterialTheme.shapes.small)
                    .clipToBounds()
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

        TextDescription(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    end = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    top = dimensionResource(id = R.dimen.catalog_margin_large)
                )
                .layoutId(SECOND_PAGE_THIRD_TEXT_ID),
            text = catalogItem.thirdDescription ?: "",
            fontSize = if (isFeatureHorizontal)
                fontDimensionResource(id = R.dimen.text_size_16) else
                fontDimensionResource(id = R.dimen.text_size_12),
            contentDescription = catalogItem.thirdDescription ?: ""
        )

        Image(
            modifier = Modifier
                .layoutId(SECOND_PAGE_FIRST_ROW_IMAGE_ID)
                .padding(start = dimensionResource(id = R.dimen.catalog_image_margin_start))
                .clip(MaterialTheme.shapes.small)
                .clipToBounds()
                .requiredWidth(dimensionResource(id = R.dimen.catalog_min_image_width))
                .requiredHeight(dimensionResource(id = R.dimen.catalog_min_image_height)),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.secondPicture)),
            contentDescription = catalogItem.firstPictureDescription
        )

        Image(
            modifier = Modifier
                .layoutId(SECOND_PAGE_SECOND_ROW_IMAGE_ID)
                .padding(end = dimensionResource(id = R.dimen.catalog_image_margin_end))
                .clip(MaterialTheme.shapes.small)
                .clipToBounds()
                .requiredWidth(dimensionResource(id = R.dimen.catalog_min_image_width))
                .requiredHeight(dimensionResource(id = R.dimen.catalog_min_image_height)),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.thirdPicture)),
            contentDescription = catalogItem.firstPictureDescription
        )
    }
}
