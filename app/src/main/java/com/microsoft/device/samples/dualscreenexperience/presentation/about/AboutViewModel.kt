/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.about

import androidx.lifecycle.ViewModel
import com.microsoft.device.samples.dualscreenexperience.domain.about.model.License
import com.microsoft.device.samples.dualscreenexperience.domain.about.usecases.GetLicensesUseCase
import com.microsoft.device.samples.dualscreenexperience.presentation.util.DataListHandler
import com.microsoft.device.samples.dualscreenexperience.presentation.util.ItemClickListener
import com.microsoft.device.samples.dualscreenexperience.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val navigator: AboutNavigator,
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

    fun navigateToNotices() {
        if (!navigator.isNavigationAtNotices()) {
            navigator.navigateToNotices()
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

    val noticeClickListener = object : ItemClickListener<Boolean> {
        override fun onClick(model: Boolean?) {
            linkToOpen.value = OPEN_IN_APP
        }
    }

    companion object {
        const val OPEN_IN_APP = "OPEN_IN_APP"
        const val ASSETS_PATH = "/assets/"
    }
}
