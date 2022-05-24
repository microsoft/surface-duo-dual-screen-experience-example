package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        modifier = modifier
    )
}

@Composable
fun TextDescription(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = fontDimensionResource(id = R.dimen.text_size_12),
    contentDescription: String
) {
    Text(
        modifier = modifier.contentDescription(contentDescription),
        text = text,
        style = TextStyle(
            color = MaterialTheme.colors.onSurface,
            fontSize = fontSize,
            fontFamily = FontFamily(Font(R.font.dmsans_regular))
        )
    )
}
