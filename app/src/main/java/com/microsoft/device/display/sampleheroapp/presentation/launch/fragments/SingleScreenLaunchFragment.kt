/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.launch.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.microsoft.device.display.sampleheroapp.databinding.FragmentSingleScreenLaunchBinding
import com.microsoft.device.display.sampleheroapp.presentation.launch.LaunchViewModel
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider

class SingleScreenLaunchFragment : Fragment(), ScreenInfoListener {

    private val viewModel: LaunchViewModel by activityViewModels()

    private var binding: FragmentSingleScreenLaunchBinding? = null

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
        setupViewPager()
    }

    private fun setupViewPager() {
        val storeDetailsAdapter = LaunchFragmentAdapter(this)
        binding?.let {
            it.launchViewPager.isSaveEnabled = false
            it.launchViewPager.adapter = storeDetailsAdapter
            TabLayoutMediator(it.launchTabLayout, it.launchViewPager) { _, _ -> }.attach()
        }

        viewModel.isDualMode.observe(viewLifecycleOwner, { binding?.isDualScreen = it })
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
