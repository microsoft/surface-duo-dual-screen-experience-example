/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.about.model

import com.microsoft.device.display.sampleheroapp.data.about.model.LicenseObject

data class License(
    val name: String,
    val license: String,
    val url: String
) {

    constructor(licenseObject: LicenseObject) :
        this(
            licenseObject.name,
            licenseObject.license,
            licenseObject.url
        )
}
