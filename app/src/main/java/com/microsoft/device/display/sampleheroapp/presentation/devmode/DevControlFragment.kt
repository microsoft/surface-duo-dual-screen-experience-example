/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.devmode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.sampleheroapp.databinding.FragmentDevControlBinding
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider

class DevControlFragment : Fragment(), ScreenInfoListener {
    private var binding: FragmentDevControlBinding? = null

    private val viewModel: DevModeViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDevControlBinding.inflate(inflater, container, false)
        binding?.isDesignPaternPresent = (viewModel.designPattern != DevModeViewModel.DesignPattern.NONE)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.devControlDesignPatterns?.setOnClickListener {
            viewModel.loadDesignPatternPage()
            openContentFragmentIfSingleScreenMode()
        }
        binding?.devControlCode?.setOnClickListener {
            viewModel.loadAppScreenPage()
            openContentFragmentIfSingleScreenMode()
        }
        binding?.devControlSdk?.setOnClickListener {
            viewModel.loadSdkComponentPage()
            openContentFragmentIfSingleScreenMode()
        }
    }

    private fun openContentFragmentIfSingleScreenMode() {
        if (rotationViewModel.isDualMode.value != true && !viewModel.isNavigationAtContent()) {
            viewModel.navigateToContent()
        }
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        if (screenInfo.isDualMode()) {
            if (!viewModel.isNavigationAtContent()) {
                viewModel.navigateToContent()
            }
        } else {
            if (viewModel.isNavigationAtContent()) {
                viewModel.navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
