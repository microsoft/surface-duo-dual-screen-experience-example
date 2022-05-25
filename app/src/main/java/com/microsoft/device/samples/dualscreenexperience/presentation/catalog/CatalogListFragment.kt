/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.microsoft.device.dualscreen.windowstate.rememberWindowState
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentCatalogBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.theme.CatalogTheme
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.view.Catalog
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogListFragment : Fragment() {

    private var binding: FragmentCatalogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatalogBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        binding?.composeView?.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CatalogTheme {
                    val windowState = appCompatActivity?.rememberWindowState()
                    if (windowState != null) {
                        val isFeatureFoldHorizontal =
                            windowState.hasFold && windowState.foldIsHorizontal

                        Catalog(
                            windowState.pane1SizeDp.width,
                            windowState.pane2SizeDp.width,
                            windowState.isDualPortrait(),
                            windowState.foldSizeDp,
                            isFeatureFoldHorizontal,
                            windowState.isSinglePortrait()
                        )
                    }
                }
            }
        }
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        appCompatActivity?.changeToolbarTitle(getString(R.string.toolbar_catalog_title))
        appCompatActivity?.setupToolbar(isBackButtonEnabled = false) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
