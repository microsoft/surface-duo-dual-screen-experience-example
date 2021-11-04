/*
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
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
