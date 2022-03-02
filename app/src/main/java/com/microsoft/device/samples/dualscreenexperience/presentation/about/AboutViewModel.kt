/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.about

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val navigator: AboutNavigator
) : ViewModel() {
    var linkToOpen = MutableLiveData("")

    fun navigateToLicenses() {
        navigator.navigateToLicenses()
    }

    fun isNavigationAtStart() = navigator.isNavigationAtStart()

    fun isNavigationAtLicenses() = navigator.isNavigationAtLicenses()

    fun navigateUp() {
        linkToOpen.value = ""
        navigator.navigateUp()
    }

    fun navigateToNotices() {
        if (!navigator.isNavigationAtNotices()) {
            navigator.navigateToNotices()
        }
    }

    companion object {
        const val ASSETS_PATH = "/assets/"
    }
}
