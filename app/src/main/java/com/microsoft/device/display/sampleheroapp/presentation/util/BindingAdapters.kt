/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.domain.store.model.StoreImage

@BindingAdapter("storeImage")
fun imageRes(view: ImageView, image: StoreImage?) {
    image?.let {
        val resId = when (image) {
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

@BindingAdapter("visibleIf")
fun showHide(view: View, shouldBeVisible: Boolean) {
    view.visibility = if (shouldBeVisible) View.VISIBLE else View.GONE
}
