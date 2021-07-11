/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.about

import android.content.res.AssetManager
import com.google.gson.Gson
import com.microsoft.device.display.sampleheroapp.config.LicensesConfig
import com.microsoft.device.display.sampleheroapp.data.Utils
import com.microsoft.device.display.sampleheroapp.data.about.model.LicenseTermsList
import com.microsoft.device.display.sampleheroapp.data.about.model.LicensesList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LicenseRepository @Inject constructor(
    private val assetManager: AssetManager,
    private val gson: Gson
) : LicenseDataSource {

    override fun getLicenseTermsList(): LicenseTermsList? =
        gson.fromJson(
            Utils.getJsonDataFromAsset(assetManager, LicensesConfig.licenseTermsFileName),
            LicenseTermsList::class.java
        )

    override fun getLicensesList(): LicensesList? =
        gson.fromJson(
            Utils.getJsonDataFromAsset(assetManager, LicensesConfig.licensesFileName),
            LicensesList::class.java
        )
}
