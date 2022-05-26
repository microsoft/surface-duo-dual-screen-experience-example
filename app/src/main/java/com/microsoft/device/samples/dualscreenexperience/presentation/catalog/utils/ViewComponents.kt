/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.microsoft.device.samples.dualscreenexperience.R

@Composable
fun BottomPageNumber(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        style = TextStyle(
            color = MaterialTheme.colors.onSurface,
            fontFamily = FontFamily(Font(R.font.dmsans_regular)),
            lineHeight = fontDimensionResource(id = R.dimen.text_size_5),
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
    fontSize: TextUnit = fontDimensionResource(id = R.dimen.text_size_12),
    lineHeight: TextUnit = fontDimensionResource(id = R.dimen.text_size_20),
    contentDescription: String,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier.contentDescription(contentDescription),
        text = text,
        lineHeight = lineHeight,
        style = TextStyle(
            color = MaterialTheme.colors.onSurface,
            fontSize = fontSize,
            fontFamily = FontFamily(Font(R.font.dmsans_regular))
        )
    )
}

@Composable
fun GuitarImage(
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
