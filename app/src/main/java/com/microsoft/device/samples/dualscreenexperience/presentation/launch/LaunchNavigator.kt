/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.launch

import androidx.navigation.FoldableNavController
import com.microsoft.device.samples.dualscreenexperience.R

class LaunchNavigator {
    private var navController: FoldableNavController? = null

    fun bind(navController: FoldableNavController) {
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
