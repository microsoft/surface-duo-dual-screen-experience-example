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
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentSingleLaunchBinding
import com.microsoft.device.display.sampleheroapp.presentation.launch.LaunchViewModel

class SingleLaunchFragment : Fragment() {

    private val viewModel: LaunchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSingleLaunchBinding.inflate(inflater, container, false)
        binding.launchListener = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager(view)
    }

    private fun setupViewPager(view: View) {
        val tabLayout = view.findViewById<TabLayout>(R.id.launch_tabs)
        val viewPager = view.findViewById<ViewPager2>(R.id.launch_viewpager)

        val storeDetailsAdapter = LaunchFragmentAdapter(this)
        viewPager.isSaveEnabled = false
        viewPager.adapter = storeDetailsAdapter
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
    }
}
