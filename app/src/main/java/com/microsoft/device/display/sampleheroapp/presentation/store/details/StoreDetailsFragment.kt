/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentStoreDetailsBinding
import com.microsoft.device.display.sampleheroapp.presentation.store.StoreViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.changeToolbarTitle
import com.microsoft.device.display.sampleheroapp.presentation.util.showToolbar

class StoreDetailsFragment : Fragment() {

    private val viewModel: StoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStoreDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        setupObservers()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.selectedStore.observe(viewLifecycleOwner, { changeActionBarTitle(it?.name) })

        activity?.showToolbar(true, viewLifecycleOwner) {
            viewModel.navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabViewPager()
    }

    private fun setupTabViewPager() {
        val tabLayout = view?.findViewById<TabLayout>(R.id.store_details_tabs)
        val viewPager = view?.findViewById<ViewPager2>(R.id.store_details_pager)

        val storeDetailsAdapter = StoreDetailsAdapter(this)
        viewPager?.adapter = storeDetailsAdapter
        viewPager?.isSaveEnabled = false
        if (tabLayout != null && viewPager != null) {
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                val textId = when (position) {
                    0 -> R.string.store_details_about_tab
                    1 -> R.string.store_details_contact_tab
                    else -> R.string.store_details_about_tab
                }
                tab.text = resources.getString(textId)
            }.attach()
        }
    }

    override fun onResume() {
        super.onResume()
        changeActionBarTitle(viewModel.selectedStore.value?.name)
    }

    private fun changeActionBarTitle(name: String?) {
        name?.let {
            activity?.changeToolbarTitle(getString(R.string.store_title, it))
        }
    }
}
