/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner

fun AppCompatActivity.changeToolbarTitle(name: String?) {
    supportActionBar?.title = name
}

fun AppCompatActivity.setupToolbar(
    isBackButtonEnabled: Boolean,
    owner: LifecycleOwner? = null,
    onBackPressedListener: (() -> Unit)? = null
) {
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
