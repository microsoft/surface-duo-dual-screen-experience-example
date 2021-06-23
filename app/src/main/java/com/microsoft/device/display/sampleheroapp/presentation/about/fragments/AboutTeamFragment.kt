/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.about.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.presentation.about.AboutViewModel

class AboutTeamFragment : Fragment(R.layout.fragment_about_team) {

    private val viewModel: AboutViewModel by activityViewModels()

    override fun onResume() {
        super.onResume()
        if (!viewModel.isNavigationAtLicenses()) {
            viewModel.navigateToLicenses()
        }
    }
}
