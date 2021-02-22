/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store.details

import android.content.Context
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
import com.microsoft.device.display.sampleheroapp.presentation.util.FragmentToolbarHandler

class StoreDetailsFragment : Fragment() {

    private val viewModel: StoreViewModel by activityViewModels()
    private var fragmentToolbarHandler: FragmentToolbarHandler? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentToolbarHandler) {
            fragmentToolbarHandler = context
        }
    }

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

        fragmentToolbarHandler?.showToolbar(true, viewLifecycleOwner) {
            viewModel.navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.store_details_tabs)
        val viewPager = view.findViewById<ViewPager2>(R.id.store_details_pager)

        val storeDetailsAdapter = StoreDetailsAdapter(this)
        viewPager.adapter = storeDetailsAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            val textId = when (position) {
                0 -> R.string.store_details_about_tab
                1 -> R.string.store_details_contact_tab
                else -> R.string.store_details_about_tab
            }
            tab.text = resources.getString(textId)
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        changeActionBarTitle(viewModel.selectedStore.value?.name)
    }

    override fun onDetach() {
        super.onDetach()
        fragmentToolbarHandler = null
    }

    private fun changeActionBarTitle(name: String?) {
        name?.let {
            fragmentToolbarHandler?.changeToolbarTitle(getString(R.string.store_title, it))
        }
    }
}
