/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.domain.product.model.Product
import com.microsoft.device.display.sampleheroapp.domain.store.model.StoreImage
import com.microsoft.device.display.sampleheroapp.presentation.product.util.StarRatingView
import com.microsoft.device.display.sampleheroapp.presentation.product.util.getProductDrawable

@BindingAdapter("storeImage")
fun getStoreImageRes(view: ImageView, image: StoreImage?) {
    image?.let {
        val resId = when (it) {
            StoreImage.JOY -> R.drawable.joy_store
            StoreImage.CESAR -> R.drawable.cesar_store
            StoreImage.HAKON -> R.drawable.hakon_store
            StoreImage.BIANCA -> R.drawable.bianca_store
            StoreImage.GUY -> R.drawable.guy_store
            StoreImage.CRISTIAN -> R.drawable.cristian_store
            StoreImage.MEHUL -> R.drawable.mehul_store
        }
        view.setImageDrawable(ResourcesCompat.getDrawable(view.resources, resId, null))
    }
}

@BindingAdapter("productImage")
fun getProductImageRes(view: ImageView, product: Product?) {
    product?.let {
        getProductDrawable(it.color, it.bodyShape).let { resId ->
            view.setImageDrawable(ContextCompat.getDrawable(view.context, resId))
        }
    }
}

@BindingAdapter("visibleIf")
fun showHide(view: View, shouldBeVisible: Boolean?) {
    view.visibility = if (shouldBeVisible == true) View.VISIBLE else View.GONE
}

@BindingAdapter("price")
fun formatPrice(view: TextView, value: Float) {
    val priceString = "$" + value.addThousandsSeparator()
    view.text = priceString
}

@BindingAdapter("ratingValue")
fun setValueToStarRatingView(view: StarRatingView, value: Float) {
    view.setValue(value)
}
