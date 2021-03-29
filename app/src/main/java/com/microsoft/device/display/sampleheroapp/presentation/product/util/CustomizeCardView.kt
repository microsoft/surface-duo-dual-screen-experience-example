/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.util

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductColor
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductType

class CustomizeCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    var productColor: ProductColor? = null
        set(value) {
            field = value
            initColor()
        }

    var productType: ProductType? = null
        set(value) {
            field = value
            initShape()
        }

    private val defaultCardRadius = context.resources.getDimension(R.dimen.card_radius)

    private val selectedCardElevation = context.resources.getDimension(R.dimen.customize_card_elevation)
    private val unselectedCardElevation = 0f

    init {
        inflate(context, R.layout.card_image_view, this)
        radius = defaultCardRadius
        useCompatPadding = true
        initAttributes(attrs)
    }

    private fun initAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomizeCardView,
            0,
            0
        ).apply {
            try {
                getInteger(R.styleable.CustomizeCardView_productColor, NOT_INIT).let {
                    if (it != NOT_INIT) {
                        productColor = ProductColor.get(it)
                    }
                }
                getInteger(R.styleable.CustomizeCardView_productType, NOT_INIT).let {
                    if (it != NOT_INIT) {
                        productType = ProductType.get(it)
                    }
                }
            } finally {
                recycle()
            }
        }
    }

    private fun initColor() {
        productColor?.let { color ->
            findViewById<ImageView>(R.id.customize_card_item).apply {
                background = ContextCompat.getDrawable(context, R.drawable.circle_color).also {
                    (it as GradientDrawable).setColor(ContextCompat.getColor(context, getColorRes(color)))
                }
            }
            contentDescription = productColor.toString()
        }
        unselect()
    }

    private fun initShape() {
        productType?.let {
            findViewById<ImageView>(R.id.customize_card_item).apply {
                setImageDrawable(ContextCompat.getDrawable(context, getTypeDrawable(it)))
            }
            contentDescription = productType.toString()
        }
        unselect()
    }

    fun unselect() {
        isSelected = false
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.gray))
        cardElevation = unselectedCardElevation
    }

    fun select() {
        isSelected = true
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.dark_blue))
        cardElevation = selectedCardElevation
    }

    companion object {
        const val NOT_INIT = -1
    }
}
