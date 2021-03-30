/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util

import android.graphics.Bitmap
import android.graphics.Matrix

fun Bitmap.rotate(degrees: Float): Bitmap =
    Bitmap.createBitmap(
        this,
        0,
        0,
        width,
        height,
        Matrix().apply { postRotate(degrees) },
        true
    )
