/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.devmode

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentDevControlBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DevControlFragment : Fragment() {
    private var binding: FragmentDevControlBinding? = null

    private val viewModel: DevModeViewModel by activityViewModels()
    private val layoutInfoViewModel: LayoutInfoViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        observeWindowLayoutInfo(context as AppCompatActivity)
    }

    private fun observeWindowLayoutInfo(activity: AppCompatActivity) {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                WindowInfoTracker.getOrCreate(activity)
                    .windowLayoutInfo(activity)
                    .collect {
                        onWindowLayoutInfoChanged(it)
                    }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDevControlBinding.inflate(inflater, container, false)
        binding?.isDesignPatternPresent = (viewModel.designPattern != DevModeViewModel.DesignPattern.NONE)
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
        if (layoutInfoViewModel.isDualMode.value != true && !viewModel.isNavigationAtContent()) {
            viewModel.navigateToContent()
        }
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        if (windowLayoutInfo.isInDualMode()) {
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
