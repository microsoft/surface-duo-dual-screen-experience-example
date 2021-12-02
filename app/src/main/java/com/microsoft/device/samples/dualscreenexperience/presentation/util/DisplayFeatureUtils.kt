/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.graphics.Rect
import android.view.View
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature

fun DisplayFeature.getBoundsInWindow(view: View?): Rect? {
    if (view == null) {
        return null
    }

    val viewRect = Rect(
        view.locationInWindow.x,
        view.locationInWindow.y,
        view.locationInWindow.x + view.width,
        view.locationInWindow.y + view.height
    )

    viewRect.left += view.paddingLeft
    viewRect.top += view.paddingTop
    viewRect.right -= view.paddingRight
    viewRect.bottom -= view.paddingBottom

    val foldingFeatureRect = Rect(bounds)
    foldingFeatureRect.offset(-view.locationInWindow.x, -view.locationInWindow.y)
    return foldingFeatureRect
}

// Foldable emulators have a 1px folding feature
fun FoldingFeature.isFoldOrSmallHinge() =
    bounds.width() <= 1 || bounds.height() <= 1
