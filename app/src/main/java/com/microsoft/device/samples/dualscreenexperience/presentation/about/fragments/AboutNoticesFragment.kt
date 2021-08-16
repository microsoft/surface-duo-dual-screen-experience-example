/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.about.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.microsoft.device.samples.dualscreenexperience.config.LicensesConfig
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentAboutNoticesBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.util.readTextFromAsset

class AboutNoticesFragment : Fragment() {

    private var binding: FragmentAboutNoticesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutNoticesBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.noticeText = context?.readTextFromAsset(LicensesConfig.softwareNoticesFileName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
