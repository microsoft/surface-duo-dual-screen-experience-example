/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.about.model

import com.microsoft.device.samples.dualscreenexperience.data.about.model.LicenseObject

data class License(
    val title: String,
    val url: String?
) {
    constructor(licenseObject: LicenseObject) : this(licenseObject.title, licenseObject.url)
}
