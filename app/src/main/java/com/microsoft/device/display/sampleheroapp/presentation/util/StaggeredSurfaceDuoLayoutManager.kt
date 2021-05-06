/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.isSurfaceDuoInDualMode
import com.microsoft.device.dualscreen.recyclerview.isDeviceInLandscape

/**
 * Class that provides a LinearLayoutManager when the device is in single screen mode and a StaggeredGridLayoutManager when the device is in spanned mode.
 */
class StaggeredSurfaceDuoLayoutManager(context: Context, screenInfo: ScreenInfo) {
    companion object {
        const val SPAN_COUNT = 2
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    fun get(): RecyclerView.LayoutManager? {
        return layoutManager
    }

    init {
        layoutManager = if (isSurfaceDuoInDualMode(screenInfo) && screenInfo.isDeviceInLandscape()) {
            StaggeredGridLayoutManager(SPAN_COUNT, RecyclerView.VERTICAL)
        } else {
            LinearLayoutManager(context)
        }
    }
}
