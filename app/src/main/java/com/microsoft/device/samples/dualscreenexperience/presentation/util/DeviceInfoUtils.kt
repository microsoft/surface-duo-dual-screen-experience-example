/*
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.content.Context
import android.view.Surface
import android.view.WindowManager

/**
 * Check if the device is SurfaceDuo
 * @return [true] if the device is SurfaceDuo, false otherwise
 */
fun Context.isSurfaceDuoDevice(): Boolean {
    val feature = "com.microsoft.device.display.displaymask"
    return packageManager.hasSystemFeature(feature)
}

/**
 * Returns a constant int for the rotation of the screen
 * according to the rotation the function will return:
 * [Surface.ROTATION_0], [Surface.ROTATION_90], [Surface.ROTATION_180], [Surface.ROTATION_270]
 * @return the screen rotation
 */
fun Context.getScreenRotation(): Int {
    return try {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.rotation
    } catch (e: IllegalStateException) {
        Surface.ROTATION_0
    }
}

fun Context.isDeviceInLandscape(): Boolean =
    getScreenRotation() == Surface.ROTATION_0 ||
        getScreenRotation() == Surface.ROTATION_180
