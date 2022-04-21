/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.devmode

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentDevContentBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.util.NetworkConnectionLiveData
import com.microsoft.device.samples.dualscreenexperience.presentation.util.RestrictedWebViewClient
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isNightMode

class DevContentFragment : Fragment() {

    private var binding: FragmentDevContentBinding? = null

    private val viewModel: DevModeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDevContentBinding.inflate(inflater, container, false)
        binding?.isLoading = true
        binding?.isConnected = true
        binding?.devContentNoInternet?.containerBackground = android.R.attr.colorBackgroundFloating
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNetworkObserver()
        setupWebView()
        setupObservers()
    }

    private fun setupNetworkObserver() {
        NetworkConnectionLiveData(activity?.applicationContext).observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                viewModel.webViewUrl.value?.let { urlValue ->
                    onWebViewStartedLoading()
                    binding?.devContentWebView?.post {
                        binding?.devContentWebView?.loadUrl(urlValue)
                    }
                }
            } else {
                binding?.devContentNoInternet?.noInternetConnectionTitle?.setText(R.string.no_internet_connection_title)
            }
            binding?.isConnected = isConnected
        }
    }

    private fun setupObservers() {
        viewModel.webViewUrl.observe(viewLifecycleOwner) {
            it?.let {
                binding?.devContentWebView?.loadUrl(it)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding?.devContentWebView?.apply {
            settings.javaScriptEnabled = true
            setBackgroundColor(Color.TRANSPARENT)
            if (context.isNightMode() && WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_ON)
            }
            webViewClient = RestrictedWebViewClient(
                acceptedHosts = viewModel.acceptedHosts,
                onAlienLinkClicked = ::openUrl,
                onProgressStarted = ::onWebViewStartedLoading,
                onProgressChanged = ::onWebViewFinishedLoading,
                onTimeoutReceived = ::onTimeoutErrorReceived
            )
        }

        selectFirstPage()
    }

    private fun selectFirstPage() {
        if (viewModel.designPattern != DevModeViewModel.DesignPattern.NONE) {
            viewModel.loadDesignPatternPage()
        } else {
            viewModel.loadAppScreenPage()
        }
    }

    private fun openUrl(url: String?) {
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    private fun onWebViewStartedLoading() {
        binding?.isLoading = true
    }

    private fun onWebViewFinishedLoading() {
        binding?.isLoading = false
    }

    private fun onTimeoutErrorReceived() {
        binding?.isConnected = false
        binding?.devContentNoInternet?.noInternetConnectionTitle?.setText(R.string.timeout_internet_connection_title)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
