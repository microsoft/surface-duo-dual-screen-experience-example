/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.devmode

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.microsoft.device.display.sampleheroapp.databinding.FragmentDevContentBinding

class DevContentFragment : Fragment() {

    private var binding: FragmentDevContentBinding? = null

    private val viewModel: DevModeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDevContentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.devContentWebView?.apply {
            settings.javaScriptEnabled = true
            setBackgroundColor(Color.TRANSPARENT)
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                WebSettingsCompat.setForceDark(settings, WebSettingsCompat.FORCE_DARK_ON)
            }
        }

        if (viewModel.designPattern != DevModeViewModel.DesignPattern.NONE) {
            viewModel.loadDesignPatternPage()
        } else {
            viewModel.loadAppScreenPage()
        }

        viewModel.webViewUrl.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    binding?.devContentWebView?.loadUrl(it)
                }
            }
        )
    }
}
