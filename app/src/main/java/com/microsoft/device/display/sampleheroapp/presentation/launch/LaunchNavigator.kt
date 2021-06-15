/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.launch

import androidx.navigation.SurfaceDuoNavController
import com.microsoft.device.display.sampleheroapp.R

class LaunchNavigator {
    private var navController: SurfaceDuoNavController? = null

    fun bind(navController: SurfaceDuoNavController) {
        this.navController = navController
    }

    fun unbind() {
        this.navController = null
    }

    fun navigateToDescription() {
        navController?.navigate(R.id.action_launch_title_to_description)
    }

    fun isNavigationAtDescription() =
        navController?.currentDestination?.id == R.id.fragment_launch_description

    fun navigateToMainFromSingle() {
        navController?.navigate(R.id.action_launch_single_to_main_activity)
    }

    fun navigateToMainFromDescription() {
        navController?.navigate(R.id.action_launch_description_to_main_activity)
    }

    fun navigateUp() {
        navController?.navigateUp()
    }
}
