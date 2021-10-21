/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.about

import android.content.res.AssetManager
import com.google.gson.Gson
import com.microsoft.device.samples.dualscreenexperience.config.LicensesConfig
import com.microsoft.device.samples.dualscreenexperience.data.about.model.LicenseList
import com.microsoft.device.samples.dualscreenexperience.data.getJsonDataFromAsset
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LicenseRepository @Inject constructor(
    private val assetManager: AssetManager,
    private val gson: Gson
) : LicenseDataSource {

    override fun getLicenseList(): LicenseList? =
        gson.fromJson(
            getJsonDataFromAsset(assetManager, LicensesConfig.licensesFileName),
            LicenseList::class.java
        )
}
