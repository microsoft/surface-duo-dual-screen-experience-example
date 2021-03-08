/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

import android.app.Activity
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import com.microsoft.device.display.sampleheroapp.R

fun Activity.changeToolbarTitle(name: String?) {
    if (this is AppCompatActivity) {
        supportActionBar?.title = name
    }
}

fun Activity.showToolbar(
    isBackButtonEnabled: Boolean,
    owner: LifecycleOwner? = null,
    onBackPressedListener: (() -> Unit)? = null
) {
    findViewById<Toolbar>(R.id.toolbar)?.visibility = View.VISIBLE
    if (this is AppCompatActivity) {
        if (isBackButtonEnabled) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
            onBackPressedListener?.let {
                val lifecycleOwner = owner ?: this
                onBackPressedDispatcher.addCallback(lifecycleOwner) { it.invoke() }
            }
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setHomeButtonEnabled(false)
        }
    }
}
