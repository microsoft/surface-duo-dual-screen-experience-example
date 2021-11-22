/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.util

import android.graphics.Rect
import android.view.Surface
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isSurfaceDuoDevice

val START_SCREEN_RECT = Rect(0, 0, 1350, 1800)
val END_SCREEN_RECT = Rect(1434, 0, 2784, 1800)

val SINGLE_SCREEN_WINDOW_RECT = START_SCREEN_RECT
val DUAL_SCREEN_WINDOW_RECT = Rect(0, 0, 2784, 1800)

val SINGLE_SCREEN_HINGE_RECT = Rect()
val DUAL_SCREEN_HINGE_RECT = Rect(1350, 0, 1434, 1800)

/**
 * Switches application from single screen mode to dual screen mode
 */
fun switchFromSingleToDualScreen() {
    if (isSurfaceDuoDevice()) {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        when (device.displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 -> device.swipe(675, 1780, 1350, 900, 400)
            Surface.ROTATION_270 -> device.swipe(1780, 675, 900, 1350, 400)
            Surface.ROTATION_90 -> device.swipe(1780, 2109, 900, 1400, 400)
        }
    }
}

/**
 * Switches application from dual screen mode to single screen
 */
fun switchFromDualToSingleScreen() {
    if (isSurfaceDuoDevice()) {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        when (device.displayRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 -> device.swipe(1500, 1780, 650, 900, 400)
            Surface.ROTATION_270 -> device.swipe(1780, 1500, 900, 650, 400)
            Surface.ROTATION_90 -> device.swipe(1780, 1250, 900, 1500, 400)
        }
    }
}

fun isSurfaceDuoDevice() =
    InstrumentationRegistry.getInstrumentation().targetContext.isSurfaceDuoDevice()

/**
 * Horizontal swipe from right to left
 */
fun horizontalSwipeToLeft() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    device.swipe(1500, 2000, 200, 2000, 10)
}

/**
 * Horizontal swipe from right to left on Left Screen
 */
fun horizontalSwipeToLeftOnLeftScreen() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    device.swipe(1000, 1000, 200, 1000, 10)
}

/**
 * Horizontal swipe from left to right on Left Screen
 */
fun horizontalSwipeToRightOnLeftScreen() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    device.swipe(200, 1000, 1000, 1000, 10)
}

/**
 * Vertical swipe from bottom to top
 */
fun verticalSwipeToTop() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    device.swipe(1000, 1000, 1000, 200, 10)
}
