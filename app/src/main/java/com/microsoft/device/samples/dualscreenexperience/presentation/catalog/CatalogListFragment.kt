/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import com.microsoft.device.dualscreen.recyclerview.isDeviceInLandscape
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentCatalogBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogListFragment : Fragment(), ViewPager.OnPageChangeListener, ScreenInfoListener {

    private val viewModel: CatalogListViewModel by activityViewModels()

    private var binding: FragmentCatalogBinding? = null
    private var catalogAdapter: CatalogListAdapter? = null

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
        setupAdapter()
        setupObservers()
    }

    private fun setupAdapter() {
        catalogAdapter = CatalogListAdapter(childFragmentManager, viewModel)
    }

    private fun setupViewPager() {
        binding?.pager?.apply {
            adapter = catalogAdapter
            currentItem = viewModel.catalogItemPosition
            addOnPageChangeListener(this@CatalogListFragment)
        }

        binding?.verticalPager?.apply {
            adapter = catalogAdapter
            currentItem = viewModel.catalogItemPosition
            addOnPageChangeListener(this@CatalogListFragment)
        }
    }

    private fun setupObservers() {
        viewModel.catalogItemList.observe(viewLifecycleOwner, { catalogAdapter?.refreshData() })
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)

        setupToolbar()
    }

    private fun setupToolbar() {
        appCompatActivity?.changeToolbarTitle(getString(R.string.nav_catalog_title))
        appCompatActivity?.setupToolbar(isBackButtonEnabled = false) {}
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        binding?.isDualPortrait = screenInfo.isDualMode() && !screenInfo.isDeviceInLandscape()
        catalogAdapter?.showTwoPages = screenInfo.isDualMode() && screenInfo.isDeviceInLandscape()
        viewModel.isScrollingEnabled.value = !screenInfo.isDualMode() && !screenInfo.isDeviceInLandscape()

        setupViewPager()
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // do nothing
    }

    override fun onPageSelected(position: Int) {
        viewModel.catalogItemPosition = position
    }

    override fun onPageScrollStateChanged(state: Int) {
        // do nothing
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        catalogAdapter = null
    }
}
