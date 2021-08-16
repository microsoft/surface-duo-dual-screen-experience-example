/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.content.Context
import android.graphics.Point
import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun View.getTopCenterPoint(): Point {
    val point = IntArray(2)
    getLocationOnScreen(point)
    point[0] += width / 2
    return Point(point[0], point[1])
}

fun RecyclerView.addOrReplaceItemDecoration(itemDecoration: RecyclerView.ItemDecoration) {
    if (itemDecorationCount == 1) {
        removeItemDecorationAt(0)
    }
    addItemDecoration(itemDecoration)
}

fun Context.readTextFromAsset(fileName: String): String =
    assets.open(fileName).bufferedReader().use { it.readText() }
