/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

import android.view.Surface
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.microsoft.device.dualscreen.ScreenInfo

class RotationViewModel : ViewModel() {
    var isDualMode = MutableLiveData(false)
    var currentRotation = MutableLiveData(Surface.ROTATION_0)
    var screenInfo = MutableLiveData<ScreenInfo?>(null)

    fun isRotated(rotation: Int?) =
        (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)

    fun isDualPortraitMode(rotation: Int?) =
        (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) && isDualMode.value == true

    companion object {
        const val ROTATE_HORIZONTALLY = 90f
    }
}
