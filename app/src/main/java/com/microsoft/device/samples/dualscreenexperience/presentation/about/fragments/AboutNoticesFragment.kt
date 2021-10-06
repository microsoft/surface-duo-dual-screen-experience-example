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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewAssetLoader.AssetsPathHandler
import androidx.webkit.WebViewFeature
import com.microsoft.device.samples.dualscreenexperience.config.LicensesConfig
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentAboutNoticesBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.about.AboutViewModel.Companion.ASSETS_PATH

class AboutNoticesFragment : Fragment() {

    private var binding: FragmentAboutNoticesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutNoticesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupWebView(view.context)
    }

    private fun setupWebView(context: Context) {
        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler(ASSETS_PATH, AssetsPathHandler(context))
            .build()

        binding?.noticeWebView?.apply {
            webViewClient = object : WebViewClient() {
                override fun shouldInterceptRequest(
                    view: WebView,
                    request: WebResourceRequest
                ): WebResourceResponse? {
                    if (request.url.toString() == LicensesConfig.softwareNoticesFilePath) {
                        return assetLoader.shouldInterceptRequest(request.url)
                    }
                    return null
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    openUrl(request?.url?.toString())
                    return true
                }
            }

            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_ON)
            }

            settings.allowFileAccess = false
            settings.allowContentAccess = false

            binding?.noticeWebView?.loadUrl(LicensesConfig.softwareNoticesFilePath)
        }
    }

    private fun openUrl(url: String?) {
        startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
