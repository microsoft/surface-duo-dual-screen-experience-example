/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Orange,
    secondary = FocusBlueGray,
    background = BackgroundGray,
    surface = SurfaceDarkBlue,
    onSecondary = BackgroundGray,
    onBackground = PrimaryGold,
)

private val LightColorPalette = lightColors(
    primary = Orange,
    secondary = FocusLightOrange,
    background = BackgroundLight,
    surface = SurfaceLight,
    onPrimary = PrimaryDarkGold,
    onBackground = PrimaryDarkGold,
)

@Composable
fun DualScreenExperienceTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
