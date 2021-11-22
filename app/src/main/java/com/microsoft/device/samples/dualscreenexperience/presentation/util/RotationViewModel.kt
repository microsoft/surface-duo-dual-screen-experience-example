/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RotationViewModel : ViewModel() {
    var isDualMode = MutableLiveData(false)
    var isFoldingFeatureVertical = MutableLiveData<Boolean?>(null)

    fun isDualPortraitMode() = isDualMode.value == true && isFoldingFeatureVertical.value == true

    companion object {
        const val ROTATE_HORIZONTALLY = 90f
    }
}
