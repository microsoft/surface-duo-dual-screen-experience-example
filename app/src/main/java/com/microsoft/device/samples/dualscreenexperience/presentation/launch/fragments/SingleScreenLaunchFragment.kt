/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.launch.fragments

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
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentSingleScreenLaunchBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.launch.LaunchViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SingleScreenLaunchFragment : Fragment() {

    private val viewModel: LaunchViewModel by activityViewModels()
    private val layoutInfoViewModel: LayoutInfoViewModel by activityViewModels()

    private var binding: FragmentSingleScreenLaunchBinding? = null

    private lateinit var windowInfoRepository: WindowInfoRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        observeWindowLayoutInfo(context as AppCompatActivity)
    }

    private fun observeWindowLayoutInfo(activity: AppCompatActivity) {
        windowInfoRepository = activity.windowInfoRepository()
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoRepository.windowLayoutInfo.collect {
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
        binding = FragmentSingleScreenLaunchBinding.inflate(inflater, container, false)
        binding?.launchListener = viewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        layoutInfoViewModel.isDualMode.observe(viewLifecycleOwner, { binding?.isDualScreen = it })
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        if (windowLayoutInfo.isInDualMode()) {
            if (!viewModel.isNavigationAtDescription()) {
                viewModel.navigateToDescription()
            }
        } else {
            if (viewModel.isNavigationAtDescription()) {
                viewModel.navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
