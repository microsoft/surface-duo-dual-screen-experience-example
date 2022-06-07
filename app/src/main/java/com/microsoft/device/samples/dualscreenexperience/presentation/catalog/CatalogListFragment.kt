/*
 *
 *  Copyright (c) Microsoft Corporation. All rights reserved.
 *  Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.dualscreen.utils.wm.getFoldingFeature
import com.microsoft.device.dualscreen.utils.wm.isFoldingFeatureVertical
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import com.microsoft.device.dualscreen.windowstate.FoldState
import com.microsoft.device.dualscreen.windowstate.rememberWindowState
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentCatalogBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.theme.CatalogTheme
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.ui.view.Catalog
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.hasExpandedWindowLayoutSize
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isFoldOrSmallHinge
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isFragmentWidthSmall
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatalogListFragment : Fragment() {

    private val viewModel: CatalogListViewModel by activityViewModels()
    private var binding: FragmentCatalogBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        observeWindowLayoutInfo(context as AppCompatActivity)
    }

    private fun observeWindowLayoutInfo(activity: AppCompatActivity) {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                WindowInfoTracker.getOrCreate(activity)
                    .windowLayoutInfo(activity)
                    .collect {
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
                        val isFoldStateHalfOpened = windowState.foldState == FoldState.HALF_OPENED

                        Catalog(
                            pane1WidthDp = windowState.pane1SizeDp.width,
                            pane2WidthDp = windowState.pane2SizeDp.width,
                            isDualScreen = windowState.isDualPortrait(),
                            foldSizeDp = windowState.foldSizeDp,
                            isFeatureHorizontal = isFeatureFoldHorizontal,
                            isSinglePortrait = windowState.isSinglePortrait(),
                            showTwoPages = viewModel.showTwoPages.observeAsState().value ?: false,
                            showSmallWindowWidthLayout =
                                viewModel.showSmallWindowWidthLayout.observeAsState().value ?: false,
                            isFoldStateHalfOpened = isFoldStateHalfOpened,
                            catalogList =
                                viewModel.catalogItemList.observeAsState().value ?: listOf()
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

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        val isLargeScreenOrHasHinge = activity?.hasExpandedWindowLayoutSize() == true ||
            windowLayoutInfo.getFoldingFeature()?.isFoldOrSmallHinge() == false
        val shouldShowTwoPages = windowLayoutInfo.isInDualMode() &&
            windowLayoutInfo.isFoldingFeatureVertical() &&
            isLargeScreenOrHasHinge
        val shouldShowSmallWindowWidthLayout =
            appCompatActivity?.isFragmentWidthSmall(
                windowLayoutInfo.getFoldingFeature(),
                shouldShowTwoPages
            )?.takeIf {
                it && !shouldShowTwoPages
            } ?: false

        viewModel.updateShowTwoPages(shouldShowTwoPages)
        viewModel.updateShowSmallWindowWidthLayout(shouldShowSmallWindowWidthLayout)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
