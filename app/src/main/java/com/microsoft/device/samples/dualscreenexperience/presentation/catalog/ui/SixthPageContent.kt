/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

const val SIXTH_PAGE_FIRST_TEXT_ID = "sixthPageFirstText"
const val SIXTH_PAGE_FIRST_IMAGE_ID = "sixthPageFirstImage"
const val SIXTH_PAGE_SECOND_TEXT_ID = "sixthPageSecondText"

@Composable
fun CatalogSixthPage(
    modifier: Modifier = Modifier,
    catalogList: List<CatalogItem>,
    isFeatureHorizontal: Boolean = false,
    showTwoPages: Boolean = false
) {
    val pageNumberOrdinal = CatalogPage.Page6.ordinal
    val catalogItem = catalogList[pageNumberOrdinal]

    val sixthPageConstraintSet = getConstraintSetForSixthPage(isFeatureHorizontal)

    PageLayout(
        modifier,
        pageNumberOrdinal + 1,
        catalogList.sizeOrZero()
    ) {
        SixthPageContent(
            modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    end = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    bottom = dimensionResource(id = R.dimen.catalog_margin_normal),
                    top = if (isFeatureHorizontal)
                        dimensionResource(id = R.dimen.catalog_margin_normal)
                    else
                        dimensionResource(id = R.dimen.zero_padding),
                ),
            sixthPageConstraintSet,
            catalogItem,
            isFeatureHorizontal,
            showTwoPages
        )
    }
}

private fun getConstraintSetForSixthPage(isFeatureHorizontal: Boolean) = ConstraintSet {
    val firstTextRef = createRefFor(SIXTH_PAGE_FIRST_TEXT_ID)
    val firstImageRef = createRefFor(SIXTH_PAGE_FIRST_IMAGE_ID)
    val secondTextRef = createRefFor(SIXTH_PAGE_SECOND_TEXT_ID)

    val horizontalGuideline = createGuidelineFromTop(0.5f)
    val topMargin = 20.dp

    constrain(firstTextRef) {
        start.linkTo(parent.start)
        top.linkTo(parent.top, topMargin)
    }

    constrain(firstImageRef) {
        linkTo(start = parent.start, end = parent.end)
        if (isFeatureHorizontal) {
            linkTo(top = firstTextRef.bottom, bottom = horizontalGuideline, topMargin = topMargin)
        } else {
            top.linkTo(firstTextRef.bottom, margin = topMargin)
        }
        width = Dimension.wrapContent
    }

    constrain(secondTextRef) {
        linkTo(start = parent.start, end = parent.end)
        if (isFeatureHorizontal) {
            top.linkTo(horizontalGuideline, margin = topMargin)
        } else {
            top.linkTo(firstImageRef.bottom, margin = topMargin)
        }
    }
}

@Composable
fun SixthPageContent(
    modifier: Modifier,
    constraintSet: ConstraintSet,
    catalogItem: CatalogItem,
    isFeatureHorizontal: Boolean,
    showTwoPages: Boolean
) {
    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = modifier
    ) {
        TextDescription(
            modifier = Modifier.layoutId(SIXTH_PAGE_FIRST_TEXT_ID),
            text = catalogItem.primaryDescription,
            contentDescription = catalogItem.primaryDescription,
            fontSize = if (isFeatureHorizontal && showTwoPages)
                fontDimensionResource(id = R.dimen.text_size_20)
            else
                fontDimensionResource(id = R.dimen.text_size_16)
        )

        RoundedImage(
            modifier = Modifier
                .layoutId(SIXTH_PAGE_FIRST_IMAGE_ID)
                .heightIn(
                    min = dimensionResource(id = R.dimen.catalog_min_image_height),
                    max = dimensionResource(id = R.dimen.catalog_max_image_height)
                ),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.firstPicture)),
            contentDescription = catalogItem.firstPictureDescription
        )

        TextDescription(
            modifier = Modifier.layoutId(SIXTH_PAGE_SECOND_TEXT_ID),
            text = catalogItem.secondaryDescription ?: "",
            contentDescription = catalogItem.secondaryDescription ?: "",
            fontSize = if (isFeatureHorizontal && showTwoPages)
                fontDimensionResource(id = R.dimen.text_size_16)
            else
                fontDimensionResource(id = R.dimen.text_size_12)
        )
    }
}
