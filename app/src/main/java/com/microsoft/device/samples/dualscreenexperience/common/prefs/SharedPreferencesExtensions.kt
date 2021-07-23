/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.common.prefs

import android.content.SharedPreferences

fun SharedPreferences.setValue(key: String, value: Boolean) {
    with(edit()) {
        putBoolean(key, value)
        apply()
    }
}
