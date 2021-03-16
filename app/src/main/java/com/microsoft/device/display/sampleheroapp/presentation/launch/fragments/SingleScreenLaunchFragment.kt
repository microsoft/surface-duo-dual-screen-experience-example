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

class SingleScreenLaunchFragment : Fragment() {

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
        setupViewPager(view)
    }

    private fun setupViewPager(view: View) {
        val storeDetailsAdapter = LaunchFragmentAdapter(this)
        binding?.let {
            it.launchViewPager.isSaveEnabled = false
            it.launchViewPager.adapter = storeDetailsAdapter
            TabLayoutMediator(it.launchTabLayout, it.launchViewPager) { _, _ -> }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
