/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.about

import androidx.lifecycle.ViewModel
import com.microsoft.device.display.sampleheroapp.domain.about.model.License
import com.microsoft.device.display.sampleheroapp.domain.about.usecases.GetLicenseTermsUseCase
import com.microsoft.device.display.sampleheroapp.domain.about.usecases.GetLicensesUseCase
import com.microsoft.device.display.sampleheroapp.presentation.util.DataListHandler
import com.microsoft.device.display.sampleheroapp.presentation.util.ItemClickListener
import com.microsoft.device.display.sampleheroapp.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val navigator: AboutNavigator,
    private val getLicenseTermsUseCase: GetLicenseTermsUseCase,
    private val getLicensesUseCase: GetLicensesUseCase
) : ViewModel() {
    var linkToOpen = SingleLiveEvent("")

    fun navigateToLicenses() {
        navigator.navigateToLicenses()
    }

    fun isNavigationAtLicenses() = navigator.isNavigationAtLicenses()

    fun navigateUp() {
        navigator.navigateUp()
    }

    val licenseTermsListHandler = object : DataListHandler<License?> {
        override fun getDataList(): List<License>? = getLicenseTermsUseCase.get()

        override fun onClick(model: License?) {
            linkToOpen.value = model?.url
        }
    }

    val licenseListHandler = object : DataListHandler<License?> {
        override fun getDataList(): List<License>? = getLicensesUseCase.get()

        override fun onClick(model: License?) {
            linkToOpen.value = model?.url
        }
    }

    val linkClickListener = object : ItemClickListener<String> {
        override fun onClick(model: String?) {
            linkToOpen.value = model
        }
    }

    companion object {
        const val OPEN_DIALOG = "OPEN_DIALOG"
    }
}
