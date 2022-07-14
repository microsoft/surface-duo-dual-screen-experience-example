/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial

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
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.presentation.util.dpToPx
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

    var currentBalloonType: TutorialBalloonType? = null

    fun show(anchorView: View, balloonType: TutorialBalloonType) {
        if (canShow()) {
            currentBalloonType = balloonType
            initContainer(buildTutorialModel(balloonType))
            initWindow()
            measureAndLayout()

            anchorView.post {
                val (xOffset, yOffset) = generateOffsets(balloonType, anchorView)
                if (anchorView.isShown) {
                    tutorialWindow?.showAsDropDown(anchorView, xOffset, yOffset)
                }
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
                    backgroundDrawableIdRes = R.drawable.bottom_left_tutorial_balloon
                    stringRes = R.string.tutorial_launch_text
                }
                TutorialBalloonType.LAUNCH_RIGHT -> {
                    backgroundDrawableIdRes = R.drawable.right_tutorial_balloon
                    stringRes = R.string.tutorial_launch_text
                }
                TutorialBalloonType.HISTORY -> {
                    backgroundDrawableIdRes = R.drawable.bottom_right_tutorial_balloon
                    stringRes = R.string.tutorial_history_text
                }
                TutorialBalloonType.DEVELOPER_MODE -> {
                    backgroundDrawableIdRes = R.drawable.top_tutorial_balloon
                    stringRes = R.string.tutorial_developer_mode_text
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
                        TutorialBalloonType.HISTORY ->
                            setPadding(halfTipPadding, microPadding, halfTipPadding, tipPadding)
                        TutorialBalloonType.DEVELOPER_MODE ->
                            setPadding(halfTipPadding, tipPadding, halfTipPadding, halfTipPadding)
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
            TutorialBalloonType.HISTORY -> {
                xOffset -= (tutorialContainer?.width ?: 0) - (tipHorizontalMargin + parent.width / 2)
            }
            TutorialBalloonType.DEVELOPER_MODE -> {
                xOffset = parent.width / 2 - (tutorialContainer?.width ?: 0) + (tipHorizontalMargin + tipHeight / 2)
                yOffset -= parent.height / 2 - tipHeight
            }
        }
        return Pair(xOffset, yOffset)
    }

    private fun canShow() = tutorialWindow == null

    fun hide() {
        tutorialWindow?.takeIf { it.isShowing }?.dismiss()
        tutorialWindow = null
        tutorialContainer = null
        currentBalloonType = null
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
    HISTORY,
    DEVELOPER_MODE
}
