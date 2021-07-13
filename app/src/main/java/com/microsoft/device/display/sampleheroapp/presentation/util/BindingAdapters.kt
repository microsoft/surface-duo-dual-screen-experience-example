/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.domain.order.model.OrderItem
import com.microsoft.device.display.sampleheroapp.domain.product.model.Product
import com.microsoft.device.display.sampleheroapp.domain.store.model.StoreImage
import com.microsoft.device.display.sampleheroapp.presentation.product.util.StarRatingView
import com.microsoft.device.display.sampleheroapp.presentation.product.util.getProductDrawable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

@BindingAdapter("assetImage")
fun getAssetImage(view: ImageView, imagePath: String?) {
    imagePath?.let {
        Uri.parse("file:///android_asset/$imagePath")?.let { imageUri ->
            Glide.with(view.context)
                .load(imageUri)
                .into(view)
        }
    }
}

@BindingAdapter("orderItemImage")
fun getOrderImageRes(view: ImageView, orderItem: OrderItem?) {
    orderItem?.let {
        getProductDrawable(it.color, it.bodyShape).let { resId ->
            view.setImageDrawable(ContextCompat.getDrawable(view.context, resId))
        }
    }
}

@BindingAdapter("visibleIf")
fun visibleIf(view: View, shouldBeVisible: Boolean?) {
    view.isVisible = shouldBeVisible == true
}

@BindingAdapter("invisibleIf")
fun invisibleIf(view: View, shouldBeInvisible: Boolean?) {
    view.isInvisible = shouldBeInvisible == true
}

@BindingAdapter("price")
fun formatPrice(view: TextView, value: Float) {
    val priceString = "$" + value.addThousandsSeparator()
    view.text = priceString
}

@BindingAdapter("orderAmount")
fun formatOrderAmount(view: TextView, value: Float) {
    val priceString = "$" + value.addThousandsSeparator()
    view.text = view.context.getString(R.string.order_amount, priceString)
}

@BindingAdapter("orderDate")
fun formatOrderDate(view: TextView, value: Long) {
    val dateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(value))
    view.text = view.context.getString(R.string.order_date, dateString)
}

@BindingAdapter("ratingValue")
fun setValueToStarRatingView(view: StarRatingView, value: Float) {
    view.setValue(value)
}
