/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class RestrictedWebViewClient(
    private val acceptedHosts: List<String>,
    private val onAlienLinkClicked: ((String?) -> Unit),
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url?.toString()
        if (Uri.parse(url).host in acceptedHosts) {
            return false
        }
        onAlienLinkClicked.invoke(url)
        return true
    }
}
