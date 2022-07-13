/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.Product
import com.microsoft.device.samples.dualscreenexperience.domain.store.model.StoreImage
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.StarRatingView
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductContentDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductDrawable

@BindingAdapter("storeImage")
fun getStoreImageRes(view: ImageView, image: StoreImage?) {
    image?.let {
        val resId = when (it) {
            StoreImage.ANA -> R.drawable.ana_store
            StoreImage.SERGIO -> R.drawable.sergio_store
            StoreImage.OVE -> R.drawable.ove_store
            StoreImage.NATASHA -> R.drawable.natasha_store
            StoreImage.BEN -> R.drawable.ben_store
            StoreImage.KRISTIAN -> R.drawable.kristian_store
            StoreImage.QUINN -> R.drawable.quinn_store
        }
        view.setImageDrawable(ContextCompat.getDrawable(view.context, resId))
    }
}

@BindingAdapter("productImage")
fun getProductImageRes(view: ImageView, product: Product?) {
    product?.let {
        getProductDrawable(it.color, it.bodyShape).let { resId ->
            view.setImageDrawable(ContextCompat.getDrawable(view.context, resId))
        }
        getProductContentDescription(it.color, it.bodyShape).let { contentDescId ->
            view.contentDescription = view.context.getString(contentDescId)
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
    val priceString = value.toPriceString()
    view.text = priceString
    view.contentDescription = view.context.getString(R.string.price_with_label, priceString)
}

@BindingAdapter("orderAmount")
fun formatOrderAmount(view: TextView, value: Float) {
    val priceString = value.toPriceString()
    view.text = view.context.getString(R.string.order_amount, priceString)
}

@BindingAdapter("orderDate")
fun formatOrderDate(view: TextView, value: Long) {
    val dateString = value.toDateString()
    view.text = view.context.getString(R.string.order_date, dateString)
}

@BindingAdapter("ratingValue")
fun setValueToStarRatingView(view: StarRatingView, value: Float) {
    view.setValue(value)
}

/**
 * For Table of contents items, removes the dots from the content description for better readability
 */
@BindingAdapter("pageContentDescription")
fun setPageContentDescription(view: TextView, value: String) {
    val titleIndex = value.indexOf('.')
    val pageNumberIndex = value.lastIndexOf('.') + 1
    val replacement = " " + view.context.getString(R.string.catalog_toc_item_content_description)

    view.contentDescription = value.replaceRange(titleIndex, pageNumberIndex, replacement)
}

/**
 * Replaces accessibility services' click action label to something more specific
 * E.g. TalkBack's default announcement for clickable items is "Double tap to activate" and this
 * replaces the "activate" label.
 */
@BindingAdapter("clickActionLabel")
fun replaceClickActionLabel(view: View, label: String?) {
    ViewCompat.replaceAccessibilityAction(
        view,
        AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK,
        label,
        null
    )
}
