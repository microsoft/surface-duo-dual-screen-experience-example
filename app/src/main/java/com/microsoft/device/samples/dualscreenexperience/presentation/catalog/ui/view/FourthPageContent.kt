/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.constraintlayout.compose.ChainStyle
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

const val FOURTH_PAGE_FIRST_IMAGE_ID = "fourthPageFirstImage"
const val FOURTH_PAGE_SECOND_IMAGE_ID = "fourthPageSecondImage"
const val FOURTH_PAGE_FIRST_TEXT_ID = "fourthPageFirstText"
const val FOURTH_PAGE_SECOND_TEXT_ID = "fourthPageSecondText"
const val FOURTH_PAGE_THIRD_TEXT_ID = "fourthPageThirdText"

@Composable
fun CatalogFourthPage(
    modifier: Modifier = Modifier,
    catalogList: List<CatalogItem>,
    isFeatureHorizontal: Boolean = false,
    showTwoPages: Boolean = false
) {
    val pageNumberOrdinal = CatalogPage.Page4.ordinal
    val catalogItem = catalogList[pageNumberOrdinal]

    val fourthPageConstraintSet = getConstraintSetForFourthPage(isFeatureHorizontal)

    PageLayout(
        modifier,
        pageNumberOrdinal + 1,
        catalogList.sizeOrZero()
    ) {
        FourthPageContent(
            modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    start = if (isFeatureHorizontal && showTwoPages)
                        dimensionResource(id = R.dimen.catalog_page_padding)
                    else
                        dimensionResource(id = R.dimen.zero_padding),
                    top = if (isFeatureHorizontal && showTwoPages)
                        dimensionResource(id = R.dimen.catalog_margin_normal)
                    else
                        dimensionResource(id = R.dimen.zero_padding)
                ),
            fourthPageConstraintSet,
            catalogItem,
            isFeatureHorizontal,
            showTwoPages
        )
    }
}

private fun getConstraintSetForFourthPage(isFeatureHorizontal: Boolean) = ConstraintSet {
    val firstImageRef = createRefFor(FOURTH_PAGE_FIRST_IMAGE_ID)
    val secondImageRef = createRefFor(FOURTH_PAGE_SECOND_IMAGE_ID)
    val firstTextRef = createRefFor(FOURTH_PAGE_FIRST_TEXT_ID)
    val secondTextRef = createRefFor(FOURTH_PAGE_SECOND_TEXT_ID)
    val thirdTextRef = createRefFor(FOURTH_PAGE_THIRD_TEXT_ID)
    val horizontalGuideline = createGuidelineFromTop(0.5f)

    constrain(firstTextRef) {
        linkTo(start = parent.start, end = parent.end)
        top.linkTo(firstImageRef.bottom)
        if (isFeatureHorizontal) {
            bottom.linkTo(horizontalGuideline)
        }
    }

    constrain(secondTextRef) {
        top.linkTo(if (isFeatureHorizontal) horizontalGuideline else firstTextRef.bottom)
        start.linkTo(firstTextRef.start)
    }

    constrain(thirdTextRef) {
        linkTo(start = firstTextRef.start, end = firstTextRef.end)
        top.linkTo(secondTextRef.bottom)
    }

    constrain(firstImageRef) {
        top.linkTo(parent.top)
        start.linkTo(secondTextRef.start)
    }

    constrain(secondImageRef) {
        start.linkTo(firstImageRef.start)
        top.linkTo(firstImageRef.top)
        end.linkTo(secondTextRef.end)
    }

    createHorizontalChain(
        firstImageRef,
        secondImageRef,
        chainStyle = ChainStyle.Spread
    )
}

@Composable
fun FourthPageContent(
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
        RoundedImage(
            modifier = Modifier
                .padding(top = dimensionResource(id = R.dimen.catalog_top_margin))
                .layoutId(FOURTH_PAGE_FIRST_IMAGE_ID)
                .requiredWidth(
                    if (isFeatureHorizontal && showTwoPages)
                        dimensionResource(id = R.dimen.catalog_max_image_height_dual_landscape)
                    else
                        dimensionResource(id = R.dimen.catalog_min_image_height)
                )
                .requiredHeight(
                    if (isFeatureHorizontal && showTwoPages)
                        dimensionResource(id = R.dimen.catalog_max_image_height)
                    else
                        dimensionResource(id = R.dimen.catalog_min_image_height)
                ),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.firstPicture)),
            contentDescription = catalogItem.firstPictureDescription
        )

        RoundedImage(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.catalog_top_margin),
                    end = if (showTwoPages)
                        dimensionResource(id = R.dimen.catalog_margin_large)
                    else
                        dimensionResource(id = R.dimen.zero_padding)
                )
                .layoutId(FOURTH_PAGE_SECOND_IMAGE_ID)
                .requiredWidth(
                    if (isFeatureHorizontal && showTwoPages)
                        dimensionResource(id = R.dimen.catalog_max_image_height_dual_landscape)
                    else
                        dimensionResource(id = R.dimen.catalog_min_image_height)
                )
                .requiredHeight(
                    if (isFeatureHorizontal && showTwoPages)
                        dimensionResource(id = R.dimen.catalog_max_image_height)
                    else
                        dimensionResource(id = R.dimen.catalog_min_image_height)
                ),
            painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.secondPicture)),
            contentDescription = catalogItem.secondPictureDescription
        )

        TextDescription(
            modifier = Modifier
                .padding(
                    horizontal = if (isFeatureHorizontal && showTwoPages)
                        dimensionResource(id = R.dimen.zero_padding)
                    else
                        dimensionResource(id = R.dimen.medium_margin)
                )
                .layoutId(FOURTH_PAGE_FIRST_TEXT_ID),
            text = catalogItem.primaryDescription,
            contentDescription = catalogItem.primaryDescription,
            fontSize = if (isFeatureHorizontal && showTwoPages)
                fontDimensionResource(id = R.dimen.text_size_16)
            else
                fontDimensionResource(id = R.dimen.text_size_12)
        )

        TextDescription(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.catalog_top_margin),
                    start = if (isFeatureHorizontal && showTwoPages)
                        dimensionResource(id = R.dimen.zero_padding)
                    else
                        dimensionResource(id = R.dimen.medium_margin)
                )
                .layoutId(FOURTH_PAGE_SECOND_TEXT_ID),
            text = catalogItem.secondaryDescription ?: "",
            contentDescription = catalogItem.secondaryDescription ?: "",
            fontSize = if (isFeatureHorizontal && showTwoPages)
                fontDimensionResource(id = R.dimen.text_size_20)
            else
                fontDimensionResource(id = R.dimen.text_size_16)
        )

        TextDescription(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.catalog_top_margin),
                    start = if (isFeatureHorizontal && showTwoPages)
                        dimensionResource(id = R.dimen.zero_padding)
                    else
                        dimensionResource(id = R.dimen.medium_margin)
                )
                .layoutId(FOURTH_PAGE_THIRD_TEXT_ID),
            text = catalogItem.thirdDescription ?: "",
            contentDescription = catalogItem.thirdDescription ?: "",
            fontSize = if (isFeatureHorizontal && showTwoPages)
                fontDimensionResource(id = R.dimen.text_size_16)
            else
                fontDimensionResource(id = R.dimen.text_size_12)
        )
    }
}
