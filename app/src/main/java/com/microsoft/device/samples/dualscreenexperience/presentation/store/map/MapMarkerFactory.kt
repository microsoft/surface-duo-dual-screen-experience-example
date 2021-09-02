/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.microsoft.device.samples.dualscreenexperience.R

class MapMarkerFactory(context: Context) {
    private var unselectedContainer: View =
        LayoutInflater.from(context).inflate(R.layout.map_marker, null)
    private var selectedContainer: View =
        LayoutInflater.from(context).inflate(R.layout.map_marker, null).apply {
            findViewById<TextView>(R.id.text_marker).background =
                ContextCompat.getDrawable(context, R.drawable.selected_marker_background)
        }
    private var infoWindowContainer: View =
        LayoutInflater.from(context).inflate(R.layout.info_window_marker, null)

    fun createBitmapWithText(text: String, isSelected: Boolean = false): Bitmap {
        val container = if (isSelected) { selectedContainer } else { unselectedContainer }
        val textView = container.findViewById<TextView>(R.id.text_marker)
        textView.text = text

        val measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        container.measure(measureSpec, measureSpec)

        val measuredWidth: Int = container.measuredWidth
        val measuredHeight: Int = container.measuredHeight

        container.layout(0, 0, measuredWidth, measuredHeight)

        val bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        container.draw(canvas)
        return bitmap
    }

    fun createGrayViewWithText(text: String): View = infoWindowContainer.also {
        it.findViewById<TextView>(R.id.text_marker).text = text
    }
}
