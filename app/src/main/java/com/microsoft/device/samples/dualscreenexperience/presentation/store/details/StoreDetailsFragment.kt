/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentStoreDetailsBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.store.StoreViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar

class StoreDetailsFragment : Fragment() {

    private val viewModel: StoreViewModel by activityViewModels()

    private var binding: FragmentStoreDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStoreDetailsBinding.inflate(inflater, container, false)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
        setupObservers()
        return binding?.root
    }

    private fun setupObservers() {
        viewModel.selectedStore.observe(viewLifecycleOwner) { setupToolbar(it?.name) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabViewPager()
    }

    private fun setupTabViewPager() {
        val storeDetailsAdapter = StoreDetailsAdapter(this)
        binding?.let {
            it.storeDetailsViewPager.adapter = storeDetailsAdapter
            it.storeDetailsViewPager.isSaveEnabled = false
            TabLayoutMediator(
                it.storeDetailsTabLayout,
                it.storeDetailsViewPager
            ) { tab, position ->
                val textId = when (position) {
                    0 -> R.string.store_details_about_tab
                    1 -> R.string.store_details_contact_tab
                    else -> R.string.store_details_about_tab
                }
                tab.text = resources.getString(textId)

                if (position != 1) {
                    tab.contentDescription =
                        resources.getString(R.string.store_accessibility_details_about)
                }
            }.attach()
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar(viewModel.selectedStore.value?.name)
    }

    private fun setupToolbar(name: String?) {
        name?.let {
            appCompatActivity?.changeToolbarTitle(getString(R.string.store_title, it))
            appCompatActivity?.setupToolbar(isBackButtonEnabled = true, viewLifecycleOwner) {
                viewModel.navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
