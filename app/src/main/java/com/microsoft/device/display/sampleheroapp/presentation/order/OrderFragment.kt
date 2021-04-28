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
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentOrderBinding
import com.microsoft.device.display.sampleheroapp.domain.order.model.Order
import com.microsoft.device.display.sampleheroapp.presentation.util.appCompatActivity
import com.microsoft.device.display.sampleheroapp.presentation.util.changeToolbarTitle
import com.microsoft.device.display.sampleheroapp.presentation.util.setupToolbar
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import com.microsoft.device.dualscreen.recyclerview.SurfaceDuoItemDecoration
import com.microsoft.device.dualscreen.recyclerview.SurfaceDuoLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment(), ScreenInfoListener {

    private var binding: FragmentOrderBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    private fun changeToolbarTitle(submittedOrder: Order?) {
        appCompatActivity?.setupToolbar(isBackButtonEnabled = false)
        if (submittedOrder == null) {
            appCompatActivity?.changeToolbarTitle(getString(R.string.toolbar_orders_title))
        } else {
            appCompatActivity?.changeToolbarTitle(getString(R.string.toolbar_orders_receipt_title))
        }
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        binding?.orderItems?.apply {
            layoutManager = SurfaceDuoLayoutManager(context, screenInfo).get()
            addItemDecoration(SurfaceDuoItemDecoration(screenInfo))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
