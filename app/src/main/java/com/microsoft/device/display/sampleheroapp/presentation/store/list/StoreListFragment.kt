/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentStoreListBinding
import com.microsoft.device.display.sampleheroapp.presentation.store.StoreViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.FragmentToolbarHandler

class StoreListFragment : Fragment() {

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
        val binding = FragmentStoreListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.cityName = viewModel.selectedCity.value?.name ?: ""
        val recyclerView = binding.storeList
        val storeAdapter = StoreAdapter(requireContext(), viewModel)
        recyclerView.adapter = storeAdapter

        viewModel.visibleStoresList.observe(
            viewLifecycleOwner,
            {
                binding.isListEmpty = it.isNullOrEmpty()
                storeAdapter.refreshData()
            }
        )

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.selectedStore.observe(
            viewLifecycleOwner,
            {
                if (it == null) {
                    changeActionBarTitle()
                }
            }
        )

        fragmentToolbarHandler?.showToolbar(true, viewLifecycleOwner) {
            viewModel.navigateUp()
        }
    }

    override fun onResume() {
        super.onResume()
        changeActionBarTitle()
    }

    override fun onDetach() {
        super.onDetach()
        fragmentToolbarHandler = null
    }

    private fun changeActionBarTitle() {
        fragmentToolbarHandler?.changeToolbarTitle(getString(R.string.toolbar_stores_title))
    }
}
