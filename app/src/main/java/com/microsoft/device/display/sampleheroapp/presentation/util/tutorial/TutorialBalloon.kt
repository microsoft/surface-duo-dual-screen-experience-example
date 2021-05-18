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
import com.microsoft.device.display.sampleheroapp.presentation.util.dpToPx
import javax.inject.Inject

class TutorialBalloon @Inject constructor(val context: Context) {

    private var tutorialContainer: View? = null
    private var tutorialWindow: PopupWindow? = null

    private val tutorialWidth = context.resources.getDimension(R.dimen.tutorial_balloon_width)
    private val tipHorizontalMargin =
        context.resources.getDimension(R.dimen.tutorial_tip_horizontal_margin).toInt()
    private val tipHeight = context.resources.getDimension(R.dimen.tutorial_tip_height).toInt()

    private val tipPadding = context.resources.getDimension(R.dimen.regular_padding).toInt()
    private val halfTipPadding = context.resources.getDimension(R.dimen.regular_padding).toInt() / 2
    private val microPadding = context.resources.getDimension(R.dimen.micro_padding).toInt()

    fun show(parent: View, balloonType: TutorialBalloonType, anchorView: View? = null) {
        if (canShow()) {
            initContainer(buildTutorialModel(balloonType))
            initWindow()
            measureAndLayout()
            parent.post {
                val (xOffset, yOffset) =
                    if (anchorView != null) {
                        generateOffsets(balloonType, anchorView)
                    } else {
                        generateOffsets(balloonType, parent)
                    }
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

    private fun buildTutorialModel(balloonType: TutorialBalloonType) =
        TutorialModel(balloonType).apply {
            when (balloonType) {
                TutorialBalloonType.LAUNCH_BOTTOM -> {
                    backgroundDrawableIdRes = R.drawable.bottom_tutorial_balloon
                    stringRes = R.string.tutorial_launch_text
                }
                TutorialBalloonType.LAUNCH_RIGHT -> {
                    backgroundDrawableIdRes = R.drawable.right_tutorial_balloon
                    stringRes = R.string.tutorial_launch_text
                }
                TutorialBalloonType.STORES -> {
                    backgroundDrawableIdRes = R.drawable.bottom_tutorial_balloon
                    stringRes = R.string.tutorial_order_text
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
                    when (model.balloonType) {
                        TutorialBalloonType.LAUNCH_BOTTOM ->
                            setPadding(halfTipPadding, microPadding, halfTipPadding, tipPadding)
                        TutorialBalloonType.LAUNCH_RIGHT ->
                            setPadding(halfTipPadding, halfTipPadding, tipPadding, halfTipPadding)
                        TutorialBalloonType.STORES ->
                            setPadding(halfTipPadding, microPadding, halfTipPadding, tipPadding)
                    }
                },
                LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
            )
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        }
    }

    private fun measureAndLayout() {
        val measureSpec = MeasureSpec.makeMeasureSpec(tutorialWidth.dpToPx(context), MeasureSpec.AT_MOST)
        tutorialContainer?.measure(measureSpec, measureSpec)

        val measuredWidth = tutorialContainer?.measuredWidth ?: 0
        val measuredHeight = tutorialContainer?.measuredHeight ?: 0

        tutorialContainer?.layout(0, 0, measuredWidth, measuredHeight)

        tutorialWindow?.width = measuredWidth
        tutorialWindow?.height = measuredHeight
    }

    private fun generateOffsets(balloonType: TutorialBalloonType, parent: View): Pair<Int, Int> {
        var xOffset = 0
        var yOffset = 0

        when (balloonType) {
            TutorialBalloonType.LAUNCH_BOTTOM -> {
                xOffset = parent.width / 2 - (tipHorizontalMargin + tipHeight / 2)
            }
            TutorialBalloonType.LAUNCH_RIGHT -> {
                xOffset = parent.width
                yOffset = parent.height / 2 + ((tutorialContainer?.height ?: 0) / 2)
            }
            TutorialBalloonType.STORES -> {
                val location = IntArray(2)
                parent.getLocationInWindow(location)
                xOffset = location[0] + parent.width / 2 - (tipHorizontalMargin + tipHeight / 2)
                yOffset = location[1]
            }
        }
        return Pair(xOffset, yOffset)
    }

    private fun canShow() = tutorialWindow?.isShowing != true

    fun hide() {
        tutorialWindow?.takeIf { it.isShowing }?.dismiss()
        tutorialWindow = null
        tutorialContainer = null
    }
}

const val TUTORIAL_TEST_ID = "Tutorial"

private data class TutorialModel(
    val balloonType: TutorialBalloonType,
    @StringRes var stringRes: Int = 0,
    @DrawableRes var backgroundDrawableIdRes: Int = 0
)

enum class TutorialBalloonType {
    LAUNCH_BOTTOM,
    LAUNCH_RIGHT,
    STORES
}
