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
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentDevControlBinding
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel

class DevControlFragment : Fragment() {
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
        if (rotationViewModel.isDualMode.value != true) {
            val contentFragment = DevContentFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.first_container_id, contentFragment, contentFragment.tag)
                .addToBackStack(null)
                .commit()
        }
    }
}
