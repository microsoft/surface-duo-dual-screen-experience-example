/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.launch.fragments

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentLaunchDescriptionBinding
import com.microsoft.device.display.sampleheroapp.presentation.launch.LaunchViewModel

class LaunchDescriptionFragment : Fragment() {

    private val viewModel: LaunchViewModel by activityViewModels()

    private var dualPatternsAnimation: AnimationDrawable? = null
    private var binding: FragmentLaunchDescriptionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLaunchDescriptionBinding.inflate(inflater, container, false)
        binding?.launchListener = viewModel
        viewModel.isDualMode.observe(viewLifecycleOwner, { binding?.shouldDisplayButton = it })
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.launchDescriptionImageView?.apply {
            setBackgroundResource(R.drawable.dual_screen_patterns_animation)
            dualPatternsAnimation = background as AnimationDrawable
        }
    }

    override fun onResume() {
        super.onResume()
        dualPatternsAnimation?.start()
    }

    override fun onPause() {
        super.onPause()
        dualPatternsAnimation?.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dualPatternsAnimation = null
        binding = null
    }
}
