/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentOrderBinding
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.StaggeredSurfaceDuoLayoutManager
import com.microsoft.device.display.sampleheroapp.presentation.util.appCompatActivity
import com.microsoft.device.display.sampleheroapp.presentation.util.changeToolbarTitle
import com.microsoft.device.display.sampleheroapp.presentation.util.setupToolbar
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import com.microsoft.device.dualscreen.recyclerview.SurfaceDuoItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment(), ScreenInfoListener {

    private val orderViewModel: OrderViewModel by activityViewModels()
    private val recommendationViewModel: OrderRecommendationsViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()

    private var orderAdapter: OrderListAdapter? = null

    private var binding: FragmentOrderBinding? = null

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

    private fun setupRecyclerView(screenInfo: ScreenInfo) {
        binding?.orderItems?.apply {
            layoutManager = StaggeredSurfaceDuoLayoutManager(context, screenInfo).get()
            if (itemDecorationCount > 0) {
                removeItemDecorationAt(0)
            }
            addItemDecoration(SurfaceDuoItemDecoration(screenInfo))
        }
    }

    private fun setupAdapter() {
        orderAdapter = OrderListAdapter(
            context = requireContext(),
            orderClickListener = orderViewModel,
            quantityDataListHandler = orderViewModel.quantityDataListHandler,
            recommendationsHandler = recommendationViewModel.orderDataHandler,
            isDualMode = rotationViewModel.isDualMode.value == true,
            isDualPortrait = rotationViewModel.isDualPortraitMode(rotationViewModel.currentRotation.value)
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
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
        setupConfirmationObservers()
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        setupRecyclerView(screenInfo)

        recommendationViewModel.refreshRecommendationList()
        updateAdapter(
            screenInfo.isDualMode(),
            rotationViewModel.isDualPortraitMode(screenInfo.getScreenRotation())
        )
    }

    private fun changeToolbarTitle() {
        appCompatActivity?.setupToolbar(isBackButtonEnabled = false)
        appCompatActivity?.changeToolbarTitle(getString(R.string.toolbar_orders_title))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        orderAdapter = null
    }
}
