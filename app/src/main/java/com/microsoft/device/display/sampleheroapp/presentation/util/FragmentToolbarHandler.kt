/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

import androidx.lifecycle.LifecycleOwner

interface FragmentToolbarHandler {
    fun changeToolbarTitle(name: String?)
    fun showToolbar(
        isBackButtonEnabled: Boolean,
        owner: LifecycleOwner? = null,
        onBackPressedListener: (() -> Unit)? = null
    )
}
