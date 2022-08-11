/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.microsoft.device.dualscreen.windowstate.WindowSizeClass
import com.microsoft.device.dualscreen.windowstate.WindowState
import com.microsoft.device.dualscreen.windowstate.getWindowSizeClass

@Composable
fun WindowState.isLandscape(): Boolean {
    return when (foldIsSeparating) {
        true -> isDualLandscape()
        false -> isSingleLandscape()
    }
}

@Composable
fun WindowState.isExpanded(): Boolean {
    return when (foldIsSeparating) {
        true ->
            getWindowSizeClass(pane1SizeDp.width) == WindowSizeClass.EXPANDED ||
                getWindowSizeClass(pane2SizeDp.width) == WindowSizeClass.EXPANDED
        false -> widthSizeClass() == WindowSizeClass.EXPANDED
    }
}

@Composable
fun WindowState.isSmallWidth(): Boolean {
    return when (isDualScreen()) {
        true -> pane1SizeDp.width.isSmallDimension() || pane2SizeDp.width.isSmallDimension()
        false -> windowWidthDp.isSmallDimension()
    }
}

@Composable
fun WindowState.isSmallHeight(): Boolean {
    return windowHeightDp.isSmallDimension()
}

@Composable
private fun Dp.isSmallDimension(): Boolean {
    return with(LocalDensity.current) { toPx() } < WIDTH_PX_BREAKPOINT
}
