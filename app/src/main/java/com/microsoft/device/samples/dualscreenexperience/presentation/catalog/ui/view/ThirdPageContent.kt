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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

const val ROW_WEIGHT = 1f

@Composable
fun CatalogThirdPage(
    modifier: Modifier = Modifier,
    catalogList: List<CatalogItem>,
    isFeatureHorizontal: Boolean = false,
    isSinglePortrait: Boolean
) {
    val pageNumberOrdinal = CatalogPage.Page3.ordinal
    val catalogItem = catalogList[pageNumberOrdinal]

    val thirdPageConstraintSet = getConstraintSetForThirdPage(isFeatureHorizontal)

    PageLayout(
        modifier,
        pageNumberOrdinal + 1,
        catalogList.sizeOrZero()
    ) {
        ThirdPageContent(
            modifier
                .padding(
                    top = if (isFeatureHorizontal)
                        dimensionResource(id = R.dimen.catalog_margin_normal) else
                        dimensionResource(id = R.dimen.zero_padding)
                )
                .verticalScroll(rememberScrollState()),
            thirdPageConstraintSet,
            catalogItem,
            isFeatureHorizontal,
            isSinglePortrait
        )
    }
}

private fun getConstraintSetForThirdPage(isFeatureHorizontal: Boolean) = ConstraintSet {
    val firstRowRef = createRefFor(THIRD_PAGE_FIRST_ROW_ID)
    val secondRowRef = createRefFor(THIRD_PAGE_SECOND_ROW_ID)
    val horizontalGuideline = createGuidelineFromTop(0.5f)

    constrain(firstRowRef) {
        linkTo(start = parent.start, startMargin = 8.dp, end = parent.end, endMargin = 8.dp)
        top.linkTo(parent.top)
        if (isFeatureHorizontal) {
            bottom.linkTo(horizontalGuideline)
        }
    }

    constrain(secondRowRef) {
        linkTo(start = parent.start, end = parent.end)
        linkTo(
            top = if (isFeatureHorizontal) horizontalGuideline else firstRowRef.bottom,
            bottom = parent.bottom
        )
    }
}

@Composable
fun ThirdPageContent(
    modifier: Modifier,
    constraintSet: ConstraintSet,
    catalogItem: CatalogItem,
    isFeatureHorizontal: Boolean,
    isSinglePortrait: Boolean
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
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.catalog_horizontal_margin)
                    )
                    .weight(ROW_WEIGHT)
                    .requiredWidth(
                        if (isSinglePortrait)
                            dimensionResource(id = R.dimen.catalog_max_image_height) else
                            dimensionResource(id = R.dimen.catalog_min_image_width)
                    )
                    .requiredHeight(
                        if (isSinglePortrait)
                            dimensionResource(id = R.dimen.catalog_max_image_height) else
                            dimensionResource(id = R.dimen.catalog_min_image_width)
                    ),
                painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.firstPicture)),
                contentDescription = catalogItem.firstPictureDescription
            )

            TextDescription(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.normal_margin),
                        start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                        end = if (isSinglePortrait)
                            dimensionResource(id = R.dimen.normal_margin) else
                            dimensionResource(id = R.dimen.catalog_horizontal_margin)
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
                    .padding(
                        start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                        end = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                        top = dimensionResource(id = R.dimen.catalog_top_margin)
                    )
                    .weight(ROW_WEIGHT)
                    .requiredWidth(
                        if (isSinglePortrait)
                            dimensionResource(id = R.dimen.catalog_max_image_height) else
                            dimensionResource(id = R.dimen.catalog_min_image_width)
                    )
                    .requiredHeight(
                        dimensionResource(id = R.dimen.catalog_min_image_width)
                    ),
                painter = rememberAsyncImagePainter(model = getImageUri(catalogItem.secondPicture)),
                contentDescription = catalogItem.secondaryDescription
            )
        }
    }
}
