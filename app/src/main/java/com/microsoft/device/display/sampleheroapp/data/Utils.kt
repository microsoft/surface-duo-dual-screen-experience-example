/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */
package com.microsoft.device.display.sampleheroapp.data

import android.content.res.AssetManager
import java.io.IOException

object Utils {
    fun getJsonDataFromAsset(assetManager: AssetManager, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = assetManager.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}
