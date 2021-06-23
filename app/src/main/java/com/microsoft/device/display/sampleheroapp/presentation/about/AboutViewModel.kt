/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.about

import androidx.lifecycle.ViewModel
import com.microsoft.device.display.sampleheroapp.presentation.about.util.License
import com.microsoft.device.display.sampleheroapp.presentation.util.DataListHandler
import com.microsoft.device.display.sampleheroapp.presentation.util.ItemClickListener
import com.microsoft.device.display.sampleheroapp.presentation.util.SingleLiveEvent
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

    val licenseTermsListHandler = object : DataListHandler<License> {
        override fun getDataList(): List<License> = licenseTermsList

        override fun onClick(model: License?) {
            linkToOpen.value = model?.url
        }
    }

    val licenseListHandler = object : DataListHandler<License> {
        override fun getDataList(): List<License> = licenseList

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
        const val OPEN_DIALOG = "INTRODUCTION_DIALOG"

        val licenseTermsList = arrayListOf(
            License(
                "Introduction to Third Party Notice",
                "Microsoft Corporation",
                OPEN_DIALOG
            ),
            License(
                "Apache License v2",
                "Apache Software Foundation",
                "https://www.apache.org/licenses/LICENSE-2.0"
            ),
            License(
                "The MIT License",
                "Massachusetts Institute of Technology",
                "https://opensource.org/licenses/MIT"
            )
        )

        val licenseList = arrayListOf(
            License(
                "Android Open Source Project",
                "Apache License v2 (c) Google, Inc. and Open Handset Alliance",
                "https://source.android.com/"
            ),
            License(
                "Android Jetpack",
                "Apache License Version 2.0, January 2004",
                "https://android.googlesource.com/platform/frameworks/support/+/androidx-main"
            ),
            License(
                "Kotlin",
                "Apache License v2 (c) JetBrains",
                "https://github.com/JetBrains/kotlin"
            ),
            License(
                "Microsoft Surface Duo SDK",
                "MIT License (c) Microsoft Corporation",
                "https://github.com/microsoft/surface-duo-sdk"
            ),
            License(
                "Material Components for Android",
                "Apache License Version 2.0, January 2004",
                "https://github.com/material-components/material-components-android"
            ),
            License(
                "Dagger2 - Hilt",
                "Apache License v2 (c) The Dagger Authors",
                "https://github.com/google/dagger"
            ),
            License(
                "Lottie Android",
                "Apache License v2 (c) Airbnb",
                "https://github.com/airbnb/lottie-android"
            ),
            License(
                "android/architecture-samples",
                "Apache License v2 (c) Google Inc. and The Android Open Source Project",
                "https://github.com/android/architecture-samples"
            )

        )
    }
}
