/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentStoreListBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.store.StoreViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar

class StoreListFragment : Fragment() {

    private val viewModel: StoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentStoreListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val recyclerView = binding.storeList
        val storeAdapter = StoreAdapter(requireContext(), viewModel)
        recyclerView.adapter = storeAdapter

        viewModel.visibleStoresList.observe(viewLifecycleOwner) {
            binding.isListEmpty = it.isNullOrEmpty()
            storeAdapter.refreshData()
        }

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.selectedStore.observe(viewLifecycleOwner) {
            if (it == null && viewModel.selectedCity.value != null) {
                setupToolbar()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        appCompatActivity?.setupToolbar(isBackButtonEnabled = true, viewLifecycleOwner) {
            viewModel.navigateUp()
        }
        appCompatActivity?.changeToolbarTitle(getString(R.string.toolbar_stores_title))
    }
}
