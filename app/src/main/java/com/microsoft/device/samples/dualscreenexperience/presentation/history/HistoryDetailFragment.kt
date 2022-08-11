/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.dualscreen.windowstate.rememberWindowState
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentHistoryDetailBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.MainActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.history.ui.OrderHistoryDetailPage
import com.microsoft.device.samples.dualscreenexperience.presentation.product.ProductViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.theme.DualScreenExperienceTheme
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isExpanded
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isLandscape
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isSmallHeight
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isSmallWidth
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryDetailFragment : Fragment() {

    private val viewModel: HistoryViewModel by activityViewModels()
    private val layoutInfoViewModel: LayoutInfoViewModel by activityViewModels()
    private val productViewModel: ProductViewModel by activityViewModels()

    private var binding: FragmentHistoryDetailBinding? = null

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

                DualScreenExperienceTheme {
                    OrderHistoryDetailPage(
                        order = viewModel.selectedOrder.observeAsState().value,
                        isDualMode = layoutInfoViewModel.isDualMode.observeAsState().value,
                        topBarPadding = appCompatActivity?.supportActionBar?.height ?: 0,
                        bottomNavPadding = (activity as? MainActivity)?.getBottomNavViewHeight() ?: 0,
                        isLandscape = windowState?.isLandscape() ?: false,
                        isExpanded = windowState?.isExpanded() ?: false,
                        isSmallWidth = windowState?.isSmallWidth() ?: false,
                        getProductFromOrderItem = productViewModel::getProductFromOrderItem,
                        addToOrder = viewModel::addItemToOrder,
                        isSmallHeight = windowState?.isSmallHeight() ?: false
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
                viewModel.reset()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
