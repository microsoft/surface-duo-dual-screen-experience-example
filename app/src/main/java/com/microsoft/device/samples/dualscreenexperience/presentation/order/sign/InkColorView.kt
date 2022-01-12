/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.order.sign

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import com.microsoft.device.samples.dualscreenexperience.R

class InkColorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var inkColor: Int? = null
        set(value) {
            field = value
            initColor()
        }

    private val parentColor = context.getColor(R.color.toggle_gray)

    private val strokeColor = context.getColor(R.color.ink_color_selected)
    private val strokeSize = context.resources.getDimension(R.dimen.color_stroke_size).toInt()

    private val viewSize = context.resources.getDimension(R.dimen.color_circle_size).toInt()

    init {
        inflate(context, R.layout.view_ink_color, this)
        initAttributes(attrs)
    }

    private fun initAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.InkColorView,
            0,
            0
        ).apply {
            try {
                getInteger(R.styleable.InkColorView_inkColor, NOT_INIT).let {
                    if (it != NOT_INIT) {
                        inkColor = it
                    }
                }
            } finally {
                recycle()
            }
        }
    }

    private fun initColor() {
        contentDescription = inkColor?.toString()
        unselect()
    }

    fun unselect() {
        isSelected = false
        findViewById<ImageView>(R.id.ink_color).apply {
            inkColor?.let {
                background = getBackgroundDrawable(it, strokeSize, parentColor)
            }
        }
    }

    fun select() {
        isSelected = true
        findViewById<ImageView>(R.id.ink_color).apply {
            inkColor?.let {
                background = getBackgroundDrawable(it, strokeSize, strokeColor)
            }
        }
    }

    private fun getBackgroundDrawable(color: Int, strokeSize: Int, strokeColor: Int) =
        GradientDrawable().also {
            it.shape = GradientDrawable.OVAL
            it.setSize(viewSize, viewSize)
            it.setStroke(strokeSize, strokeColor)
            it.setColor(color)
        }

    companion object {
        const val NOT_INIT = -1
    }
}