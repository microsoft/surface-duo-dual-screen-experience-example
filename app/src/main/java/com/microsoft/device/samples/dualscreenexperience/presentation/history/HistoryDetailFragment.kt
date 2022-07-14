/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.history

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
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import com.microsoft.device.dualscreen.windowstate.WindowSizeClass
import com.microsoft.device.dualscreen.windowstate.rememberWindowState
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentHistoryDetailBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.MainActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.history.ui.OrderHistoryDetailPage
import com.microsoft.device.samples.dualscreenexperience.presentation.theme.DualScreenExperienceTheme
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryDetailFragment : Fragment() {

    private val viewModel: HistoryViewModel by activityViewModels()
    private val layoutInfoViewModel: LayoutInfoViewModel by activityViewModels()

    private var binding: FragmentHistoryDetailBinding? = null

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
        binding = FragmentHistoryDetailBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        binding?.composeView?.apply {
            // Dispose of the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val windowState = activity?.rememberWindowState()
                var isLandscape = false
                var isExpanded = false
                windowState?.let {
                    isLandscape = it.isDualLandscape() || it.isSingleLandscape()
                    isExpanded = layoutInfoViewModel.isDualMode.value == false &&
                        it.widthSizeClass() == WindowSizeClass.EXPANDED
                }

                DualScreenExperienceTheme {
                    OrderHistoryDetailPage(
                        order = viewModel.selectedOrder.observeAsState().value,
                        showTwoPages = layoutInfoViewModel.isDualMode.observeAsState().value,
                        topBarPadding = appCompatActivity?.supportActionBar?.height ?: 0,
                        bottomNavPadding = (activity as? MainActivity)?.getBottomNavViewHeight() ?: 0,
                        isLandscape = isLandscape,
                        isExpanded = isExpanded
                    )
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
        if (layoutInfoViewModel.isDualMode.value == false) {
            appCompatActivity?.changeToolbarTitle(getString(R.string.toolbar_history_details_title))
            appCompatActivity?.setupToolbar(isBackButtonEnabled = true, viewLifecycleOwner) {
                viewModel.navigateUp()
                viewModel.selectedOrder.value = null
            }
        }
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        if (windowLayoutInfo.isInDualMode()) {
            viewModel.selectFirstOrder()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
