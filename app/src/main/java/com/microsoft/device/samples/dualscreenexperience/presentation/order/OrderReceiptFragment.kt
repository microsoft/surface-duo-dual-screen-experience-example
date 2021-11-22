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
import android.widget.Toast
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
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentOrderReceiptBinding
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.Product
import com.microsoft.device.samples.dualscreenexperience.presentation.util.DataListHandler
import com.microsoft.device.samples.dualscreenexperience.presentation.util.RotationViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar
import com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial.TutorialViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderReceiptFragment : Fragment() {

    private val orderViewModel: OrderViewModel by activityViewModels()
    private val recommendationViewModel: OrderRecommendationsViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()
    private val tutorialViewModel: TutorialViewModel by activityViewModels()

    private var orderAdapter: OrderListAdapter? = null

    private var binding: FragmentOrderReceiptBinding? = null

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
        binding = FragmentOrderReceiptBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        showTutorialIfNeeded()
        showSuccessMessageIfNeeded()
    }

    private fun setupRecyclerView(windowLayoutInfo: WindowLayoutInfo) {
        binding?.orderReceiptItems?.apply {
            layoutManager = FoldableStaggeredLayoutManager(context, windowLayoutInfo).get()
            replaceItemDecorationAt(FoldableItemDecoration(windowLayoutInfo))
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
            isDualPortrait = rotationViewModel.isDualPortraitMode(),
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
        changeToolbarTitle()
    }

    private fun onScreenInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        setupRecyclerView(windowLayoutInfo)

        updateAdapter(
            windowLayoutInfo.isInDualMode(),
            rotationViewModel.isDualPortraitMode()
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
