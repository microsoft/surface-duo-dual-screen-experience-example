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
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.config.LicensesConfig
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentAboutLicensesBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.about.AboutViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.about.AboutViewModel.Companion.ASSETS_PATH
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.addClickableLink

class AboutLicensesFragment : Fragment() {

    private var binding: FragmentAboutLicensesBinding? = null

    private val viewModel: AboutViewModel by activityViewModels()
    private val layoutInfoViewModel: LayoutInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutLicensesBinding.inflate(inflater, container, false)
        binding?.isDualMode = layoutInfoViewModel.isDualMode.value
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        layoutInfoViewModel.isDualMode.observe(viewLifecycleOwner) { binding?.isDualMode = it }
    }

    private fun setupListeners() {
        binding?.licensePrivacyTitle?.setOnClickListener {
            onNoticeClicked(LicensesConfig.PRIVACY_URL)
        }
        binding?.licenseTermsTitle?.setOnClickListener {
            onOssLicensesClicked()
        }
        binding?.licenseTermsOtherTitle?.setOnClickListener {
            onNoticeClicked(LicensesConfig.OTHER_NOTICES_PATH)
        }
        setupDescriptionText()
    }

    private fun onOssLicensesClicked() {
        activity?.let {
            OssLicensesMenuActivity.setActivityTitle(getString(R.string.about_licenses_terms_title))
            startActivity(Intent(it, OssLicensesMenuActivity::class.java))
        }
    }

    private fun onNoticeClicked(noticeUrl: String) {
        noticeUrl.takeIf { it.isNotBlank() }?.let { url -> openUrl(url) }
        viewModel.linkToOpen.value = noticeUrl
    }

    private fun setupDescriptionText() {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                openUrl(getString(R.string.github_issues_url))
            }
        }

        binding?.feedbackSingleScreenDescription?.addClickableLink(
            getString(R.string.about_feedback_description_clickable),
            clickableSpan
        )
    }

    private fun openUrl(url: String) {
        if (url.contains(ASSETS_PATH)) {
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
