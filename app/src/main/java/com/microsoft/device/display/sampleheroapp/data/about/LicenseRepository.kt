/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.about

import android.content.Context
import com.google.gson.Gson
import com.microsoft.device.display.sampleheroapp.config.LicensesConfig
import com.microsoft.device.display.sampleheroapp.data.about.model.LicenseTermsList
import com.microsoft.device.display.sampleheroapp.data.about.model.LicensesList
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LicenseRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val gson: Gson
) : LicenseDataSource {

    override fun getLicenseTermsList(): LicenseTermsList? =
        gson.fromJson(
            getJsonDataFromAsset(LicensesConfig.licenseTermsFileName),
            LicenseTermsList::class.java
        )

    override fun getLicensesList(): LicensesList? =
        gson.fromJson(
            getJsonDataFromAsset(LicensesConfig.licensesFileName),
            LicensesList::class.java
        )

    private fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            jsonString = appContext.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}
