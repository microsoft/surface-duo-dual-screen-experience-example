/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

val Fragment.appCompatActivity: AppCompatActivity?
    get() = activity as? AppCompatActivity

const val DEBUG_TAG = "HISTORY_TEST"

fun Fragment.logOnResume() {
    logFunctionCall("onResume")
}

fun Fragment.logOnPause() {
    this.logFunctionCall("onPause")
}

fun Fragment.logFunctionCall(functionName: String) {
    this.javaClass.logFunctionCall(functionName)
}

fun <T : Any> Class<T>.logFunctionCall(functionName: String) {
    Log.d(DEBUG_TAG, "${this.simpleName}: $functionName")
}
