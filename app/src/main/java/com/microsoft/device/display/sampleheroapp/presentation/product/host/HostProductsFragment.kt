/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.host

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.config.MapConfig.TEST_MODE_ENABLED
import com.microsoft.device.display.sampleheroapp.databinding.FragmentHostProductsBinding
import com.microsoft.device.display.sampleheroapp.presentation.util.appCompatActivity
import com.microsoft.device.display.sampleheroapp.presentation.util.changeToolbarTitle
import com.microsoft.device.display.sampleheroapp.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostProductsFragment : Fragment() {

    private var binding: FragmentHostProductsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHostProductsBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabViewPager()
    }

    private fun setupTabViewPager() {
        val pageAdapter = HostProductsFragmentAdapter(this)
        binding?.let {
            it.catalogViewPager.adapter = pageAdapter
            it.catalogViewPager.isSaveEnabled = false
            TabLayoutMediator(
                it.catalogTabLayout,
                it.catalogViewPager,
                true,
                shouldUseAnimations()
            ) { tab, position ->
                val textId = when (position) {
                    0 -> R.string.main_products_tab_catalog
                    1 -> R.string.main_products_tab_products
                    else -> R.string.main_products_tab_catalog
                }
                tab.text = resources.getString(textId)
            }.attach()

            it.catalogTabLayout.addOnTabSelectedListener(
                object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        it.catalogViewPager.isUserInputEnabled = tab?.position != 0
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }
                }
            )
            it.catalogViewPager.isUserInputEnabled = false
        }
    }

    private fun shouldUseAnimations(): Boolean = !TEST_MODE_ENABLED

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        appCompatActivity?.changeToolbarTitle(getString(R.string.app_name))
        appCompatActivity?.setupToolbar(isBackButtonEnabled = false) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
