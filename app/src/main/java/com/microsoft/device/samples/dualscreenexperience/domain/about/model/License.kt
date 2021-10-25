/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.about.model

import com.microsoft.device.samples.dualscreenexperience.data.about.model.LicenseData

data class License(
    val title: String,
    val url: String?
) {
    constructor(licenseData: LicenseData) : this(licenseData.title, licenseData.url)
}
