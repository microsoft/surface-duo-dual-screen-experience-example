/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.about.fragments

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentAboutLicensesBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.about.AboutViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.about.AboutViewModel.Companion.OPEN_DIALOG
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
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapters()
        setupObservers()
    }

    private fun setupAdapters() {
        binding?.licenseTermsRecyclerView?.adapter =
            LicenseListAdapter(requireContext(), viewModel.licenseTermsListHandler)
        binding?.licenseTermsRecyclerView?.setHasFixedSize(true)

        binding?.licenseRecyclerView?.adapter =
            LicenseListAdapter(requireContext(), viewModel.licenseListHandler)
        binding?.licenseRecyclerView?.setHasFixedSize(true)
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
        if (url == OPEN_DIALOG) {
            openIntroductionDialog()
        } else {
            startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    private fun openIntroductionDialog() {
        AlertDialog.Builder(requireContext(), R.style.Theme_App_Dialog)
            .setTitle(R.string.introduction_to_third_party_notice_title)
            .setMessage(R.string.introduction_to_third_party_notice_message)
            .setPositiveButton(R.string.introduction_to_third_party_notice_button) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
