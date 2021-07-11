/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.sampleheroapp.databinding.FragmentCatalogBinding
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.recyclerview.isDeviceInLandscape
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogListFragment : Fragment(), ScreenInfoListener {

    private val listViewModel: CatalogListViewModel by activityViewModels()
    private var binding: FragmentCatalogBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatalogBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val catalogAdapter =
            CatalogListAdapter(requireActivity().supportFragmentManager, listViewModel)
        binding?.pager?.adapter = catalogAdapter
        listViewModel.catalogItemList.observe(viewLifecycleOwner, { catalogAdapter.refreshData() })
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        (binding?.pager?.adapter as CatalogListAdapter).showTwoPages =
            screenInfo.isDualMode() && screenInfo.isDeviceInLandscape()
    }
}
