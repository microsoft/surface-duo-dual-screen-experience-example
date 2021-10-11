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
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentAboutTeamBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.about.AboutViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.addClickableLink

class AboutTeamFragment : Fragment(R.layout.fragment_about_team), ScreenInfoListener {

    private var binding: FragmentAboutTeamBinding? = null

    private val viewModel: AboutViewModel by activityViewModels()

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

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        if (!viewModel.isNavigationAtLicenses()) {
            viewModel.navigateToLicenses()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
