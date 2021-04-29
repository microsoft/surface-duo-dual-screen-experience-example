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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentOrderBinding
import com.microsoft.device.display.sampleheroapp.domain.order.model.Order
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.appCompatActivity
import com.microsoft.device.display.sampleheroapp.presentation.util.changeToolbarTitle
import com.microsoft.device.display.sampleheroapp.presentation.util.setupToolbar
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialViewModel
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import com.microsoft.device.dualscreen.recyclerview.SurfaceDuoItemDecoration
import com.microsoft.device.dualscreen.recyclerview.SurfaceDuoLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment(), ScreenInfoListener {

    private val orderViewModel: OrderViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()
    private val tutorialViewModel: TutorialViewModel by activityViewModels()
    private val recommendationViewModel: OrderRecommendationsViewModel by activityViewModels()

    var orderAdapter: OrderListAdapter? = null

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
        orderViewModel.resetItemList()

        setupListObservers()
        setupConfirmationObservers()
    }

    private fun setupListObservers() {
        orderViewModel.itemList.observe(
            viewLifecycleOwner,
            {
                if (orderAdapter == null) {
                    setupAdapter()
                } else {
                    updateAdapter(
                        rotationViewModel.isDualMode.value == true,
                        rotationViewModel.isDualPortraitMode(rotationViewModel.currentRotation.value)
                    )
                }
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
                it?.let {
                    disableEditing()
                }
                changeToolbarTitle(it)
            }
        )
        orderViewModel.showSuccessMessage.observe(
            viewLifecycleOwner,
            {
                if (it == true) {
                    showSuccessMessage()
                    showTutorialIfNeeded()
                }
            }
        )
    }

    private fun setupRecyclerView(screenInfo: ScreenInfo) {
        binding?.orderItems?.apply {
            layoutManager = SurfaceDuoLayoutManager(context, screenInfo).get()
            addItemDecoration(SurfaceDuoItemDecoration(screenInfo))
        }
    }

    private fun setupAdapter() {
        orderAdapter = OrderListAdapter(
            requireContext(),
            orderViewModel,
            recommendationViewModel,
            rotationViewModel.isDualMode.value == true,
            rotationViewModel.isDualPortraitMode(rotationViewModel.currentRotation.value)
        )

        binding?.orderItems?.adapter = orderAdapter
    }

    private fun disableEditing() {
        orderAdapter?.disableEditing()
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

    private fun changeToolbarTitle(submittedOrder: Order?) {
        appCompatActivity?.setupToolbar(isBackButtonEnabled = false)
        if (submittedOrder == null) {
            appCompatActivity?.changeToolbarTitle(getString(R.string.toolbar_orders_title))
        } else {
            appCompatActivity?.changeToolbarTitle(getString(R.string.toolbar_orders_receipt_title))
        }
    }

    private fun showSuccessMessage() {
        activity?.let {
            Toast(it).apply {
                view = layoutInflater.inflate(
                    R.layout.toast_layout,
                    it.findViewById(R.id.toast_container)
                )
                duration = Toast.LENGTH_LONG
                show()
            }
        }
    }

    private fun showTutorialIfNeeded() {
        tutorialViewModel.updateTutorial()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        orderAdapter = null
    }
}
