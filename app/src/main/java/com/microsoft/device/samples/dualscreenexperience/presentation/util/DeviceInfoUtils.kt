/*
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.dualscreen.utils.wm.extractFoldingFeatureRect
import com.microsoft.device.dualscreen.utils.wm.getScreenRectangles
import com.microsoft.device.dualscreen.utils.wm.getWindowRect
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import com.microsoft.device.dualscreen.utils.wm.normalizeWindowRect

/**
 * Check if the device is SurfaceDuo
 * @return [true] if the device is SurfaceDuo, false otherwise
 */
fun Context.isSurfaceDuoDevice(): Boolean {
    val feature = "com.microsoft.device.display.displaymask"
    return packageManager.hasSystemFeature(feature)
}

fun Activity.isInLandscape() =
    resources.configuration.orientation == ORIENTATION_LANDSCAPE

fun Activity.isFragmentInLandscape(windowLayoutInfo: WindowLayoutInfo): Boolean {
    if (!windowLayoutInfo.isInDualMode()) {
        return isInLandscape()
    }
    val windowRect = normalizeWindowRect(
        windowLayoutInfo.extractFoldingFeatureRect(),
        getWindowRect(),
        resources.configuration.orientation
    )
    val fragmentRect =
        getScreenRectangles(windowLayoutInfo.extractFoldingFeatureRect(), windowRect)?.firstOrNull()

    return fragmentRect?.let {
        it.width() - it.height() > 0
    } ?: false
}

enum class WindowLayoutSize(val breakpointWidth: Float, val breakpointHeight: Float) {
    COMPACT(breakpointWidth = 600f, breakpointHeight = 480f),
    MEDIUM(breakpointWidth = 880f, breakpointHeight = 900f),
    EXPANDED(breakpointWidth = Float.MAX_VALUE, breakpointHeight = Float.MAX_VALUE)
}

fun Activity.isFragmentWidthSmall(
    foldingFeature: FoldingFeature?,
    showsTwoPages: Boolean
): Boolean =
    if (foldingFeature != null && showsTwoPages) {
        val windowRect = normalizeWindowRect(
            foldingFeature.bounds,
            getWindowRect(),
            resources.configuration.orientation
        )
        getScreenRectangles(foldingFeature.bounds, windowRect)?.firstOrNull()?.width()
    } else {
        getWindowRect().width()
    }?.let {
        it.toFloat() <= 1200f
    } ?: false

val Activity.widthWindowLayoutSize: WindowLayoutSize
    get() {
        val widthDp = getWindowRect().width() / resources.displayMetrics.density
        return when {
            widthDp < WindowLayoutSize.COMPACT.breakpointWidth -> WindowLayoutSize.COMPACT
            widthDp < WindowLayoutSize.MEDIUM.breakpointWidth -> WindowLayoutSize.MEDIUM
            else -> WindowLayoutSize.EXPANDED
        }
    }

val Activity.heightWindowLayoutSize: WindowLayoutSize
    get() {
        val heightDp = getWindowRect().height() / resources.displayMetrics.density
        return when {
            heightDp < WindowLayoutSize.COMPACT.breakpointHeight -> WindowLayoutSize.COMPACT
            heightDp < WindowLayoutSize.MEDIUM.breakpointHeight -> WindowLayoutSize.MEDIUM
            else -> WindowLayoutSize.EXPANDED
        }
    }

fun Activity.hasExpandedWindowLayoutSize(): Boolean =
    widthWindowLayoutSize == WindowLayoutSize.EXPANDED ||
        heightWindowLayoutSize == WindowLayoutSize.EXPANDED
