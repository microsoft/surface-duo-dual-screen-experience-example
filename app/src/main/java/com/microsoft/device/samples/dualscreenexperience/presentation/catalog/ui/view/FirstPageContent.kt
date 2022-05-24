package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.BottomPageNumber
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.contentDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils.fontDimensionResource
import com.microsoft.device.samples.dualscreenexperience.presentation.util.sizeOrZero

const val TABLE_OF_CONTENTS_ID = "tableOfContents"
const val BOTTOM_PAGE_NUMBER_ID = "bottomPageNumber"
const val FIRST_TEXT_ID = "firstText"
const val SECOND_TEXT_ID = "secondText"
const val THIRD_TEXT_ID = "thirdText"
const val FOURTH_TEXT_ID = "fourthText"
const val FIFTH_TEXT_ID = "fifthText"

@Composable
fun CatalogFirstPage(modifier: Modifier = Modifier, catalogList: List<CatalogItem>) {
    val catalogItem = catalogList[CatalogPage.Page1.ordinal]

    val constraintSet = getMainConstraintSet()

    ConstraintLayout(constraintSet = constraintSet, modifier = modifier) {
        TableOfContents(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = dimensionResource(id = R.dimen.catalog_margin_normal))
                .layoutId(TABLE_OF_CONTENTS_ID),
            catalogItem = catalogItem
        )

        BottomPageNumber(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    end = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    bottom = dimensionResource(id = R.dimen.catalog_margin_small)
                )
                .layoutId(BOTTOM_PAGE_NUMBER_ID),
            text = stringResource(
                id = R.string.catalog_page_no,
                CatalogPage.Page1.ordinal + 1,
                catalogList.sizeOrZero()
            )
        )
    }
}

@Composable
private fun getMainConstraintSet() = ConstraintSet {
    val contentRef = createRefFor(TABLE_OF_CONTENTS_ID)
    val bottomPageNumberRef = createRefFor(BOTTOM_PAGE_NUMBER_ID)

    constrain(contentRef) {
        linkTo(start = parent.start, end = parent.end)
        linkTo(top = parent.top, bottom = bottomPageNumberRef.top)
    }

    constrain(bottomPageNumberRef) {
        start.linkTo(parent.start)
        bottom.linkTo(parent.bottom)
    }
}

@Composable
private fun getConstraintSetForTableOfContents() = ConstraintSet {
    val firstTextRef = createRefFor(FIRST_TEXT_ID)
    val secondTextRef = createRefFor(SECOND_TEXT_ID)
    val thirdTextRef = createRefFor(THIRD_TEXT_ID)
    val fourthTextRef = createRefFor(FOURTH_TEXT_ID)
    val fifthTextRef = createRefFor(FIFTH_TEXT_ID)

    constrain(firstTextRef) {
        start.linkTo(parent.start)
        top.linkTo(parent.top)
    }

    constrain(secondTextRef) {
        end.linkTo(parent.end)
        start.linkTo(parent.start)
        top.linkTo(firstTextRef.bottom)
    }

    constrain(thirdTextRef) {
        end.linkTo(parent.end)
        start.linkTo(parent.start)
        top.linkTo(secondTextRef.bottom)
    }

    constrain(fourthTextRef) {
        end.linkTo(parent.end)
        start.linkTo(parent.start)
        top.linkTo(thirdTextRef.bottom)
    }

    constrain(fifthTextRef) {
        end.linkTo(parent.end)
        start.linkTo(parent.start)
        top.linkTo(fourthTextRef.bottom)
    }
}

@Composable
fun TableOfContents(modifier: Modifier = Modifier, catalogItem: CatalogItem) {
    val constraintSet = getConstraintSetForTableOfContents()

    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
                    top = dimensionResource(id = R.dimen.catalog_margin_very_large),
                    end = dimensionResource(id = R.dimen.catalog_horizontal_margin)
                )
                .layoutId(FIRST_TEXT_ID),
            text = catalogItem.primaryDescription,
            style = TextStyle(
                color = MaterialTheme.colors.onSurface,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontSize = fontDimensionResource(id = R.dimen.text_size_18),
                textAlign = TextAlign.Start
            )
        )

        ContentTextItem(
            modifier = Modifier
                .contentDescription(
                    getPageContentDescription(
                        catalogItem.secondaryDescription ?: "",
                        stringResource(id = R.string.catalog_toc_item_content_description)
                    )
                )
                .padding(top = dimensionResource(id = R.dimen.catalog_margin_normal))
                .layoutId(SECOND_TEXT_ID),
            text = catalogItem.secondaryDescription ?: "",
            destinationPage = 2,
            onItemClick = { }
        )

        ContentTextItem(
            modifier = Modifier
                .contentDescription(
                    getPageContentDescription(
                        catalogItem.thirdDescription ?: "",
                        stringResource(id = R.string.catalog_toc_item_content_description)
                    )
                )
                .layoutId(THIRD_TEXT_ID),
            text = catalogItem.thirdDescription ?: "",
            destinationPage = 3,
            onItemClick = { }
        )

        ContentTextItem(
            modifier = Modifier
                .contentDescription(
                    getPageContentDescription(
                        catalogItem.fourthDescription ?: "",
                        stringResource(id = R.string.catalog_toc_item_content_description)
                    )
                )
                .layoutId(FOURTH_TEXT_ID),
            text = catalogItem.fourthDescription ?: "",
            destinationPage = 4,
            onItemClick = { }
        )

        ContentTextItem(
            modifier = Modifier
                .contentDescription(
                    getPageContentDescription(
                        catalogItem.fifthDescription ?: "",
                        stringResource(id = R.string.catalog_toc_item_content_description)
                    )
                )
                .layoutId(FIFTH_TEXT_ID),
            text = catalogItem.fifthDescription ?: "",
            destinationPage = 5,
            onItemClick = { }
        )

    }
}

@Composable
fun ContentTextItem(
    modifier: Modifier = Modifier,
    text: String,
    destinationPage: Int,
    onItemClick: (Int) -> Unit,
) {
    Text(
        modifier = modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.catalog_margin_small),
                horizontal = dimensionResource(id = R.dimen.catalog_horizontal_margin)
            )
            .wrapContentWidth()
            .clickable { onItemClick(destinationPage) },
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = TextStyle(
            color = MaterialTheme.colors.onSurface,
            fontFamily = FontFamily(Font(R.font.dmsans_regular)),
            lineHeight = 5.sp,
            fontSize = fontDimensionResource(id = R.dimen.text_size_16),
            textAlign = TextAlign.Start,
        )
    )
}

fun getPageContentDescription(value: String, replacement: String): String {
    val titleIndex = value.indexOf('.')
    val pageNumberIndex = value.lastIndexOf('.') + 1

    return value.replaceRange(titleIndex, pageNumberIndex, replacement)
}
