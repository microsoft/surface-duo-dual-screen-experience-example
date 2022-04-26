/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.about

import androidx.lifecycle.ViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val navigator: AboutNavigator
) : ViewModel() {
    var internalLinkToOpen = SingleLiveEvent("")

    fun navigateToLicenses() {
        navigator.navigateToLicenses()
    }

    fun isNavigationAtStart() = navigator.isNavigationAtStart()

    fun navigateUp() {
        internalLinkToOpen.value = ""
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
