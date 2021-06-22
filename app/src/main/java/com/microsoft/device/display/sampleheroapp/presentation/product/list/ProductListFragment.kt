/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentProductListBinding
import com.microsoft.device.display.sampleheroapp.presentation.product.ProductViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.appCompatActivity
import com.microsoft.device.display.sampleheroapp.presentation.util.changeToolbarTitle
import com.microsoft.device.display.sampleheroapp.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val viewModel: ProductViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()
    private var binding: FragmentProductListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productAdapter = ProductAdapter(requireContext(), viewModel)
        binding?.productList?.adapter = productAdapter

        viewModel.productList.observe(viewLifecycleOwner, { productAdapter.refreshData() })
        rotationViewModel.isDualMode.observe(
            viewLifecycleOwner,
            {
                if (it == true && viewModel.selectedProduct.value == null) {
                    viewModel.navigateToDetails()
                }
            }
        )
    }

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
