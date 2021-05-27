/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

import android.graphics.Point
import android.view.View

fun View.getTopCenterPoint(): Point {
    val point = IntArray(2)
    getLocationOnScreen(point)
    point[0] += width / 2
    return Point(point[0], point[1])
}
