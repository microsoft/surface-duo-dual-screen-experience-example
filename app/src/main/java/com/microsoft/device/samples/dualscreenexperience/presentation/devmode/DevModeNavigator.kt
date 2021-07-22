/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.devmode

import androidx.navigation.SurfaceDuoNavController
import com.microsoft.device.samples.dualscreenexperience.R

class DevModeNavigator {
    private var navController: SurfaceDuoNavController? = null

    fun bind(navController: SurfaceDuoNavController) {
        this.navController = navController
    }

    fun unbind() {
        this.navController = null
    }

    fun navigateToContent() {
        navController?.navigate(R.id.action_dev_control_to_content)
    }

    fun isNavigationAtContent() =
        navController?.currentDestination?.id == R.id.fragment_dev_content

    fun isNavigationAtStart() =
        navController?.currentDestination?.id == R.id.fragment_dev_control

    fun navigateUp() {
        navController?.navigateUp()
    }
}
