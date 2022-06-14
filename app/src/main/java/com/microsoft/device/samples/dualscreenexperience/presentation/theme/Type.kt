/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.microsoft.device.samples.dualscreenexperience.R

val Typography = Typography(
    h5 = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto)),
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.sp
    ),
    body1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.dmsans_regular)),
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp,
        fontSize = 16.sp,
        letterSpacing = 0.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        color = Color.Gray
    )
)
