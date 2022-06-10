/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.microsoft.device.samples.dualscreenexperience.R

const val CONTENT_ID = "tableOfContents"
const val BOTTOM_PAGE_NUMBER_ID = "bottomPageNumber"

@Composable
fun PageLayout(
    modifier: Modifier = Modifier,
    pageNumber: Int,
    maxPageNumber: Int,
    content: @Composable () -> Unit
) {
    val constraintSet = getMainConstraintSet()

    ConstraintLayout(constraintSet = constraintSet, modifier = modifier) {
        Box(
            modifier = Modifier
                .layoutId(CONTENT_ID)
                .fillMaxHeight()
                .padding(bottom = dimensionResource(id = R.dimen.catalog_margin_normal))
        ) {
            content()
        }

        BottomPageNumber(
            modifier = Modifier.layoutId(BOTTOM_PAGE_NUMBER_ID),
            text = stringResource(
                id = R.string.catalog_page_no, pageNumber, maxPageNumber
            )
        )
    }
}

@Composable
private fun getMainConstraintSet() = ConstraintSet {
    val contentRef = createRefFor(CONTENT_ID)
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
fun BottomPageNumber(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        style = TextStyle(
            color = MaterialTheme.colors.onSurface,
            fontFamily = FontFamily(Font(R.font.dmsans_regular)),
            lineHeight = fontDimensionResource(id = R.dimen.text_size_4),
            fontSize = fontDimensionResource(id = R.dimen.text_size_12),
            textAlign = TextAlign.Start
        ),
        modifier = modifier.padding(
            start = dimensionResource(id = R.dimen.catalog_horizontal_margin),
            end = dimensionResource(id = R.dimen.catalog_horizontal_margin),
            bottom = dimensionResource(id = R.dimen.catalog_margin_small)
        )
    )
}

@Composable
fun TextDescription(
    modifier: Modifier = Modifier,
    text: String,
    contentDescription: String,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    color: Color = MaterialTheme.colors.onSurface,
    textAlign: TextAlign = TextAlign.Start,
    fontSize: TextUnit = fontDimensionResource(id = R.dimen.text_size_12),
    lineHeight: TextUnit = fontDimensionResource(id = R.dimen.text_size_20)
) {
    Text(
        modifier = modifier.contentDescription(contentDescription),
        text = text,
        lineHeight = lineHeight,
        textAlign = textAlign,
        style = textStyle,
        color = color,
        fontSize = fontSize
    )
}

@Composable
fun RoundedImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String?,
    contentScale: ContentScale = ContentScale.None
) {
    Image(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clipToBounds(),
        painter = painter,
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}

@Composable
fun ContentTextItem(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onSurface,
    textAlign: TextAlign = TextAlign.Start,
    text: String,
    destinationPage: Int,
    onItemClick: (Int) -> Unit,
) {
    Row(
        modifier = modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.catalog_margin_normal),
                horizontal = dimensionResource(id = R.dimen.catalog_horizontal_margin)
            )
            .wrapContentWidth()
            .clickable { onItemClick(destinationPage - 1) },
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body1,
            color = color,
            textAlign = textAlign
        )
        Text(text = destinationPage.toString(), color = color)
    }
}
