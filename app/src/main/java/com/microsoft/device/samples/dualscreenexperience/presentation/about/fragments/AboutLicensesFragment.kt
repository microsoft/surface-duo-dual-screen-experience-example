/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.about.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentAboutLicensesBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.about.AboutViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.about.AboutViewModel.Companion.OPEN_IN_APP
import com.microsoft.device.samples.dualscreenexperience.presentation.util.RotationViewModel

class AboutLicensesFragment : Fragment() {

    private var binding: FragmentAboutLicensesBinding? = null

    private val viewModel: AboutViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutLicensesBinding.inflate(inflater, container, false)
        binding?.isDualMode = rotationViewModel.isDualMode.value
        binding?.linksItems?.itemClickListener = viewModel.linkClickListener
        binding?.noticeClickListener = viewModel.noticeClickListener
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    private fun setupObservers() {
        rotationViewModel.isDualMode.observe(viewLifecycleOwner, { binding?.isDualMode = it })
        viewModel.linkToOpen.observe(
            viewLifecycleOwner,
            {
                it?.takeIf { it.isNotBlank() }?.let { url -> openUrl(url) }
            }
        )
    }

    private fun openUrl(url: String) {
        if (url == OPEN_IN_APP) {
            viewModel.navigateToNotices()
        } else {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
