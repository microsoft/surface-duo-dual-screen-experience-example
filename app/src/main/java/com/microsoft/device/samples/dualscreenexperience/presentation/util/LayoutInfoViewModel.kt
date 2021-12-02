/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.window.layout.FoldingFeature

class LayoutInfoViewModel : ViewModel() {
    var isDualMode = MutableLiveData(false)
    var foldingFeature = MutableLiveData<FoldingFeature?>(null)

    fun isDualPortraitMode() = isDualMode.value == true &&
        foldingFeature.value?.orientation == FoldingFeature.Orientation.VERTICAL

    companion object {
        const val ROTATE_HORIZONTALLY = 90f
    }
}
