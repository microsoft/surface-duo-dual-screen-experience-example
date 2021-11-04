/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.order

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.dualscreen.recyclerview.FoldableItemDecoration
import com.microsoft.device.dualscreen.recyclerview.FoldableStaggeredLayoutManager
import com.microsoft.device.dualscreen.recyclerview.utils.replaceItemDecorationAt
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentOrderBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.util.RotationViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderFragment : Fragment() {

    private val orderViewModel: OrderViewModel by activityViewModels()
    private val recommendationViewModel: OrderRecommendationsViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()

    private var orderAdapter: OrderListAdapter? = null

    private var binding: FragmentOrderBinding? = null

    private lateinit var windowInfoRepository: WindowInfoRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        observeWindowLayoutInfo(context as AppCompatActivity)
    }

    private fun observeWindowLayoutInfo(activity: AppCompatActivity) {
        windowInfoRepository = activity.windowInfoRepository()
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoRepository.windowLayoutInfo.collect {
                    onScreenInfoChanged(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupListObservers()
    }

    private fun setupListObservers() {
        orderViewModel.itemList.observe(
            viewLifecycleOwner,
            {
                recommendationViewModel.refreshRecommendationList()
                orderAdapter?.refreshItems()
            }
        )

        recommendationViewModel.productList.observe(
            viewLifecycleOwner,
            {
                recommendationViewModel.refreshRecommendationList()
                orderAdapter?.refreshRecommendations()
            }
        )
    }

    private fun setupConfirmationObservers() {
        orderViewModel.submittedOrder.observe(
            viewLifecycleOwner,
            {
                if (it != null) {
                    orderViewModel.navigateToReceipt()
                } else {
                    changeToolbarTitle()
                }
            }
        )
    }

    private fun setupRecyclerView(windowLayoutInfo: WindowLayoutInfo) {
        binding?.orderItems?.apply {
            layoutManager = FoldableStaggeredLayoutManager(context, windowLayoutInfo).get()
            replaceItemDecorationAt(FoldableItemDecoration(windowLayoutInfo))
        }
    }

    private fun setupAdapter() {
        orderAdapter = OrderListAdapter(
            context = requireContext(),
            orderClickListener = orderViewModel,
            quantityDataListHandler = orderViewModel.quantityDataListHandler,
            recommendationsHandler = recommendationViewModel.orderDataHandler,
            isDualMode = rotationViewModel.isDualMode.value == true,
            isDualPortrait = rotationViewModel.isDualPortraitMode()
        )

        binding?.orderItems?.adapter = orderAdapter
    }

    private fun updateAdapter(dualMode: Boolean, dualPortrait: Boolean) {
        orderAdapter?.apply {
            isDualMode = dualMode
            isDualPortrait = dualPortrait
            refreshItems()
        }
    }

    override fun onResume() {
        super.onResume()
        setupConfirmationObservers()
        changeToolbarTitle()
    }

    private fun onScreenInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        setupRecyclerView(windowLayoutInfo)

        recommendationViewModel.refreshRecommendationList()
        updateAdapter(
            windowLayoutInfo.isInDualMode(),
            rotationViewModel.isDualPortraitMode()
        )
    }

    private fun changeToolbarTitle() {
        appCompatActivity?.setupToolbar(isBackButtonEnabled = false) {}
        appCompatActivity?.changeToolbarTitle(getString(R.string.toolbar_orders_title))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        orderAdapter = null
    }
}
