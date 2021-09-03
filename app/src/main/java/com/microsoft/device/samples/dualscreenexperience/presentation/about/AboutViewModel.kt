/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.about

import androidx.lifecycle.ViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.ItemClickListener
import com.microsoft.device.samples.dualscreenexperience.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val navigator: AboutNavigator
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
