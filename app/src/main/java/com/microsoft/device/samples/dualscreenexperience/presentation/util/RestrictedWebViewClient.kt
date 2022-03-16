/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util

import android.graphics.Bitmap
import android.net.Uri
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class RestrictedWebViewClient(
    private val acceptedHosts: List<String>,
    private val onAlienLinkClicked: ((String?) -> Unit),
    private val onProgressStarted: (() -> Unit)? = null,
    private val onProgressChanged: (() -> Unit)? = null,
    private val onTimeoutReceived: (() -> Unit)? = null
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url?.toString()
        if (Uri.parse(url).host in acceptedHosts) {
            return false
        }
        onAlienLinkClicked.invoke(url)
        return true
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        onProgressStarted?.invoke()
    }

    override fun onPageCommitVisible(view: WebView?, url: String?) {
        super.onPageCommitVisible(view, url)
        onProgressChanged?.invoke()
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        if (error?.errorCode == ERROR_TIMEOUT) {
            onTimeoutReceived?.invoke()
        }
    }
}
