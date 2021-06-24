/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.about.usecases

import com.microsoft.device.display.sampleheroapp.data.about.LicenseDataSource
import com.microsoft.device.display.sampleheroapp.domain.about.model.License
import javax.inject.Inject

class GetLicensesUseCase @Inject constructor(private val licenseRepository: LicenseDataSource) {
    fun get(): List<License>? = licenseRepository.getLicensesList()?.licenses?.map { License(it) }
}
