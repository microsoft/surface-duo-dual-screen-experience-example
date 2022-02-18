/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.product.util

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.presentation.util.dpToPx
import kotlin.math.roundToInt

class StarRatingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var ratingValue = 0f
    private val textViewId = R.id.star_rating_text
    private val starMargin = context.resources.getDimension(R.dimen.nano_margin)
    private val heightView = context.resources.getDimension(R.dimen.star_rating_height)

    init {
        validateRating()
        initView()
        addChildViews()
    }

    private fun initView() {
        orientation = HORIZONTAL
    }

    private fun addChildViews() {
        for (index in MIN_STARS until STAR_COUNT) {
            addView(
                AppCompatImageView(context).apply {
                    id = getStarImageId(index)
                    setImageResource(R.drawable.ic_star_empty)
                    gravity = Gravity.CENTER_VERTICAL
                    importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
                },
                LayoutParams(heightView.dpToPx(context), heightView.dpToPx(context)).apply {
                    marginEnd = starMargin.dpToPx(context)
                }
            )
        }
        addView(
            TextView(context, null, 0, R.style.GoldText).apply {
                id = textViewId
                text = ratingValue.toString()
                textSize = heightView
                contentDescription = context.getString(R.string.store_accessibility_rating, ratingValue.toString())
            },
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                marginStart = starMargin.dpToPx(context)
            }
        )
    }

    fun setValue(newValue: Float) {
        validateRating()

        ratingValue = newValue

        resetImageValue()
        resetTextValue()
    }

    private fun resetImageValue() {
        for (index in MIN_STARS until ratingValue.roundToInt()) {
            val imageId = getStarImageId(index)
            findViewById<AppCompatImageView>(imageId).setImageResource(R.drawable.ic_star_full)
        }
    }

    private fun resetTextValue() {
        findViewById<TextView>(textViewId).apply {
            text = ratingValue.toString()
            contentDescription = context.getString(R.string.store_accessibility_rating, ratingValue.toString())
        }
    }

    private fun getStarImageId(index: Int) =
        when (index + 1) {
            1 -> R.id.star_rating_1
            2 -> R.id.star_rating_2
            3 -> R.id.star_rating_3
            4 -> R.id.star_rating_4
            5 -> R.id.star_rating_5
            else -> R.id.star_rating_1
        }

    private fun validateRating() {
        if (ratingValue < MIN_STARS || ratingValue > STAR_COUNT) {
            throw IllegalStateException("Rating should be a float value between $MIN_STARS and $STAR_COUNT")
        }
    }

    companion object {
        const val STAR_COUNT = 5
        const val MIN_STARS = 0
    }
}
