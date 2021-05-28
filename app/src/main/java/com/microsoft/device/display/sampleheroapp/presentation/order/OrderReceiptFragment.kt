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
import com.microsoft.device.display.sampleheroapp.databinding.FragmentOrderReceiptBinding
import com.microsoft.device.display.sampleheroapp.domain.product.model.Product
import com.microsoft.device.display.sampleheroapp.presentation.util.DataListHandler
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.StaggeredSurfaceDuoLayoutManager
import com.microsoft.device.display.sampleheroapp.presentation.util.addOrReplaceItemDecoration
import com.microsoft.device.display.sampleheroapp.presentation.util.appCompatActivity
import com.microsoft.device.display.sampleheroapp.presentation.util.changeToolbarTitle
import com.microsoft.device.display.sampleheroapp.presentation.util.setupToolbar
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialViewModel
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import com.microsoft.device.dualscreen.recyclerview.SurfaceDuoItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderReceiptFragment : Fragment(), ScreenInfoListener {

    private val orderViewModel: OrderViewModel by activityViewModels()
    private val recommendationViewModel: OrderRecommendationsViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()
    private val tutorialViewModel: TutorialViewModel by activityViewModels()

    private var orderAdapter: OrderListAdapter? = null

    private var binding: FragmentOrderReceiptBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderReceiptBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        showTutorialIfNeeded()
        showSuccessMessageIfNeeded()
    }

    private fun setupRecyclerView(screenInfo: ScreenInfo) {
        binding?.orderReceiptItems?.apply {
            layoutManager = StaggeredSurfaceDuoLayoutManager(context, screenInfo).get()
            addOrReplaceItemDecoration(SurfaceDuoItemDecoration(screenInfo))
        }
    }

    private fun setupAdapter() {
        val receiptDataHandler = object : DataListHandler<Product> {
            override fun getDataList(): List<Product>? = recommendationViewModel.getRecommendationsList()
            override fun onClick(model: Product?) {
                recommendationViewModel.onItemClick(model)
                orderViewModel.navigateToOrder()
            }
        }

        orderAdapter = OrderListAdapter(
            context = requireContext(),
            quantityDataListHandler = orderViewModel.submittedDataListHandler,
            submittedOrder = orderViewModel.submittedOrder.value,
            recommendationsHandler = receiptDataHandler,
            isDualMode = rotationViewModel.isDualMode.value == true,
            isDualPortrait = rotationViewModel.isDualPortraitMode(rotationViewModel.currentRotation.value),
            isEditEnabled = false
        )

        binding?.orderReceiptItems?.adapter = orderAdapter
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
        changeToolbarTitle()
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        setupRecyclerView(screenInfo)

        updateAdapter(
            screenInfo.isDualMode(),
            rotationViewModel.isDualPortraitMode(screenInfo.getScreenRotation())
        )
    }

    private fun changeToolbarTitle() {
        appCompatActivity?.changeToolbarTitle(getString(R.string.toolbar_orders_receipt_title))
        appCompatActivity?.setupToolbar(isBackButtonEnabled = true, viewLifecycleOwner) {
            orderViewModel.navigateToOrder()
        }
    }

    private fun showTutorialIfNeeded() {
        tutorialViewModel.updateTutorial()
    }

    private fun showSuccessMessageIfNeeded() {
        if (orderViewModel.showSuccessMessage) {
            activity?.let {
                Toast(it).apply {
                    view = layoutInflater.inflate(
                        R.layout.toast_layout,
                        it.findViewById(R.id.toast_container)
                    )
                    duration = Toast.LENGTH_SHORT
                    show()
                    orderViewModel.showSuccessMessage = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        orderAdapter = null
    }
}
