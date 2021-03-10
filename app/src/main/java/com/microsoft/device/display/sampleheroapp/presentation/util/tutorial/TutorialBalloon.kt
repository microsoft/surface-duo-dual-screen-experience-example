/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util.tutorial

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.microsoft.device.display.sampleheroapp.R

class TutorialBalloon(private val context: Context) {

    private var tutorialContainer: View? = null
    private var tutorialWindow: PopupWindow? = null

    private val tutorialWidth = context.resources.getDimension(R.dimen.tutorial_balloon_width)
    private val tipHorizontalMargin =
        context.resources.getDimension(R.dimen.tutorial_tip_horizontal_margin).toInt()
    private val tipHeight = context.resources.getDimension(R.dimen.tutorial_tip_height).toInt()

    private val tipPadding = context.resources.getDimension(R.dimen.regular_padding).toInt()
    private val halfTipPadding = context.resources.getDimension(R.dimen.regular_padding).toInt() / 2
    private val microPadding = context.resources.getDimension(R.dimen.micro_padding).toInt()

    fun show(parent: View, type: TutorialType) {
        if (canShow()) {
            initContainer(buildTutorialModel(type))
            initWindow()
            measureAndLayout()
            parent.post {
                val (xOffset, yOffset) = generateOffsets(type, parent.width, parent.height)
                tutorialWindow?.showAsDropDown(parent, xOffset, yOffset)
            }
        }
    }

    private fun initWindow() {
        tutorialWindow =
            PopupWindow(
                tutorialContainer,
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
    }

    private fun buildTutorialModel(type: TutorialType) =
        TutorialModel(type).apply {
            when (type) {
                TutorialType.LAUNCH_BOTTOM -> {
                    backgroundDrawableIdRes = R.drawable.bottom_tutorial_balloon
                    stringRes = R.string.tutorial_launch_text
                }
                TutorialType.LAUNCH_RIGHT -> {
                    backgroundDrawableIdRes = R.drawable.right_tutorial_balloon
                    stringRes = R.string.tutorial_launch_text
                }
            }
        }

    private fun initContainer(model: TutorialModel) {
        tutorialContainer = FrameLayout(context).apply {
            contentDescription = TUTORIAL_TEST_ID
            addView(
                TextView(context, null, 0, R.style.TutorialText).apply {
                    id = R.id.tutorial_balloon_text
                    model.stringRes.takeIf { it != 0 }?.let {
                        text = context.getString(it)
                    }

                    model.backgroundDrawableIdRes.takeIf { it != 0 }?.let {
                        background = ContextCompat.getDrawable(context, it)
                    }
                    when (model.type) {
                        TutorialType.LAUNCH_BOTTOM ->
                            setPadding(halfTipPadding, microPadding, halfTipPadding, tipPadding)
                        TutorialType.LAUNCH_RIGHT ->
                            setPadding(halfTipPadding, halfTipPadding, tipPadding, halfTipPadding)
                    }
                },
                LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            )
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        }
    }

    private fun measureAndLayout() {
        val measureSpec = MeasureSpec.makeMeasureSpec(dpToPixel(tutorialWidth), MeasureSpec.AT_MOST)
        tutorialContainer?.measure(measureSpec, measureSpec)

        val measuredWidth = tutorialContainer?.measuredWidth ?: 0
        val measuredHeight = tutorialContainer?.measuredHeight ?: 0

        tutorialContainer?.layout(0, 0, measuredWidth, measuredHeight)

        tutorialWindow?.width = measuredWidth
        tutorialWindow?.height = measuredHeight
    }

    private fun generateOffsets(type: TutorialType, width: Int, height: Int): Pair<Int, Int> {
        var xOffset = 0
        var yOffset = 0

        when (type) {
            TutorialType.LAUNCH_BOTTOM -> {
                xOffset = width / 2 - (tipHorizontalMargin + tipHeight / 2)
            }
            TutorialType.LAUNCH_RIGHT -> {
                xOffset = width
                yOffset = height / 2 + ((tutorialContainer?.height ?: 0) / 2)
            }
        }
        return Pair(xOffset, yOffset)
    }

    private fun canShow() = tutorialWindow?.isShowing != true

    fun hide() {
        tutorialWindow?.takeIf { it.isShowing }?.dismiss()
        tutorialWindow = null
    }

    private fun dpToPixel(dp: Float) =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            context.resources.displayMetrics
        ).toInt()
}

const val TUTORIAL_TEST_ID = "Tutorial"

data class TutorialModel(
    val type: TutorialType,
    @StringRes var stringRes: Int = 0,
    @DrawableRes var backgroundDrawableIdRes: Int = 0
)

enum class TutorialType {
    LAUNCH_BOTTOM,
    LAUNCH_RIGHT
}
