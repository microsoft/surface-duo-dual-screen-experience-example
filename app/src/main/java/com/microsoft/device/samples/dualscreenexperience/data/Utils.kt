/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */
package com.microsoft.device.samples.dualscreenexperience.data

import android.content.res.AssetManager
import java.io.IOException

fun getJsonDataFromAsset(assetManager: AssetManager, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = assetManager.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: IOException) {
        return null
    }
    return jsonString
}
