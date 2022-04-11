/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog

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
import androidx.viewpager.widget.ViewPager
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.dualscreen.utils.wm.getFoldingFeature
import com.microsoft.device.dualscreen.utils.wm.isFoldingFeatureVertical
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentCatalogBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.hasExpandedWindowLayoutSize
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isFoldOrSmallHinge
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatalogListFragment : Fragment(), ViewPager.OnPageChangeListener {

    private val viewModel: CatalogListViewModel by activityViewModels()

    private var binding: FragmentCatalogBinding? = null
    private var catalogAdapter: CatalogListAdapter? = null

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
            currentItem = viewModel.catalogItemPosition.value ?: 0
            addOnPageChangeListener(this@CatalogListFragment)
        }
    }

    private fun setupObservers() {
        viewModel.catalogItemList.observe(viewLifecycleOwner) { catalogAdapter?.refreshData() }
        viewModel.catalogItemPosition.observe(viewLifecycleOwner) { newPageNumber ->
            binding?.pager?.apply {
                if (currentItem != newPageNumber) {
                    setCurrentItem(newPageNumber, true)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        appCompatActivity?.changeToolbarTitle(getString(R.string.toolbar_catalog_title))
        appCompatActivity?.setupToolbar(isBackButtonEnabled = false) {}
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        val isLargeScreenOrHasHinge = activity?.hasExpandedWindowLayoutSize() == true ||
            windowLayoutInfo.getFoldingFeature()?.isFoldOrSmallHinge() == false

        catalogAdapter?.showTwoPages = windowLayoutInfo.isInDualMode() &&
            windowLayoutInfo.isFoldingFeatureVertical() &&
            isLargeScreenOrHasHinge
        viewModel.showTwoPages.value = catalogAdapter?.showTwoPages ?: false

        setupViewPager()
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        // do nothing
    }

    override fun onPageSelected(position: Int) {
        viewModel.catalogItemPosition.value = position
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
