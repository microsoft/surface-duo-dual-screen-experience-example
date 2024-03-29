/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.about

import androidx.navigation.FoldableNavController
import com.microsoft.device.samples.dualscreenexperience.R

class AboutNavigator {
    private var navController: FoldableNavController? = null

    fun bind(navController: FoldableNavController) {
        this.navController = navController
    }

    fun unbind() {
        this.navController = null
    }

    fun navigateToLicenses() {
        navController?.navigate(R.id.action_about_team_to_licenses)
    }

    fun navigateToNotices() {
        navController?.navigate(R.id.action_about_licenses_to_notices)
    }

    fun isNavigationAtNotices() =
        navController?.currentDestination?.id == R.id.fragment_about_notices

    fun isNavigationAtLicenses() =
        navController?.currentDestination?.id == R.id.fragment_about_licenses

    fun isNavigationAtStart() =
        navController?.currentDestination?.id == R.id.fragment_about_team

    fun navigateUp() {
        navController?.navigateUp()
    }
}
