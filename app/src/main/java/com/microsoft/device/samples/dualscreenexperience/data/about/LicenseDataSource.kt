/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.about

import com.microsoft.device.samples.dualscreenexperience.data.about.model.LicenseList

interface LicenseDataSource {
    fun getLicenseList(): LicenseList?
}
