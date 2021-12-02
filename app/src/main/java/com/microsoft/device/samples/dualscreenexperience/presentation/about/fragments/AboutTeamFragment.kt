/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.about.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.style.ClickableSpan
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
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentAboutTeamBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.about.AboutViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.addClickableLink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AboutTeamFragment : Fragment(R.layout.fragment_about_team) {

    private var binding: FragmentAboutTeamBinding? = null

    private val viewModel: AboutViewModel by activityViewModels()

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
        binding = FragmentAboutTeamBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDescriptionText()
    }

    private fun setupDescriptionText() {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.github_issues_url))
                    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }

        binding?.feedbackDescription?.addClickableLink(
            getString(R.string.about_feedback_description_clickable),
            clickableSpan
        )
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        if (!viewModel.isNavigationAtLicenses()) {
            viewModel.navigateToLicenses()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
