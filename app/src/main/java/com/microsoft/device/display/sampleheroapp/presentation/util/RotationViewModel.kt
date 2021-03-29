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

class RotationViewModel : ViewModel() {
    var currentRotation = MutableLiveData(Surface.ROTATION_0)

    fun isRotated(rotation: Int) =
        (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)

    companion object {
        const val ROTATE_HORIZONTALLY = 90f
    }
}
