/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.about

import com.microsoft.device.display.sampleheroapp.data.about.model.LicenseTermsList
import com.microsoft.device.display.sampleheroapp.data.about.model.LicensesList

interface LicenseDataSource {
    fun getLicenseTermsList(): LicenseTermsList?
    fun getLicensesList(): LicensesList?
}
