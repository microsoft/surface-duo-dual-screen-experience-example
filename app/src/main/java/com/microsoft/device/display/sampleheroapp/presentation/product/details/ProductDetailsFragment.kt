/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentProductDetailsBinding
import com.microsoft.device.display.sampleheroapp.presentation.product.ProductViewModel
import com.microsoft.device.display.sampleheroapp.presentation.product.customize.ProductCustomizeActivity
import com.microsoft.device.display.sampleheroapp.presentation.product.customize.ProductCustomizeViewModel.Companion.SELECTED_PRODUCT_ID
import com.microsoft.device.display.sampleheroapp.presentation.product.util.getProductDrawable
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private val viewModel: ProductViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()

    private var binding: FragmentProductDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        binding?.viewModel = viewModel
        binding?.rotationViewModel = rotationViewModel
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.productDetailsFrets?.descriptionString =
            getString(R.string.product_details_frets_description)

        binding?.productDetailsPickup?.titleString =
            getString(R.string.product_details_pickup_title)

        binding?.productDetailsPickup?.descriptionString =
            getString(R.string.product_details_pickup_description)

        viewModel.selectedProduct.observe(
            viewLifecycleOwner,
            {
                binding?.productDetailsFrets?.titleString =
                    getString(R.string.product_details_frets_title, it?.fretsNumber ?: 0)
            }
        )

        binding?.productDetailsImage?.apply {
            viewModel.selectedProduct.value?.let {
                if (it.colorId != null && it.bodyShape != null) {
                    setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            getProductDrawable(it.colorId, it.bodyShape)
                        )
                    )
                }
            }
        }

        viewModel.productList.observe(viewLifecycleOwner, { viewModel.selectFirstProduct() })

        binding?.productDetailsCustomizeButton?.setOnClickListener {
            startActivity(
                Intent(requireContext(), ProductCustomizeActivity::class.java).apply {
                    viewModel.selectedProduct.value?.productId?.let {
                        putExtra(SELECTED_PRODUCT_ID, it)
                    }
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
