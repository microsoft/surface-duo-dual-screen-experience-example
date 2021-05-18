/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.customize

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.airbnb.lottie.LottieAnimationView
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentProductCustomizeDetailsBinding
import com.microsoft.device.display.sampleheroapp.presentation.product.util.getProductContentDescription
import com.microsoft.device.display.sampleheroapp.presentation.product.util.getProductDrawable
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductCustomizeDetailsFragment : Fragment() {

    private val viewModel: ProductCustomizeViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()

    private var binding: FragmentProductCustomizeDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductCustomizeDetailsBinding.inflate(inflater, container, false)
        binding?.viewModel = viewModel
        binding?.rotationViewModel = rotationViewModel
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBindingFields()
        setupObservers()
        setupListeners()
    }

    private fun initBindingFields() {
        binding?.productDetailsFrets?.descriptionString =
            getString(R.string.product_details_frets_description)

        binding?.productDetailsPickup?.titleString =
            getString(R.string.product_details_pickup_title)

        binding?.productDetailsPickup?.descriptionString =
            getString(R.string.product_details_pickup_description)
    }

    private fun setupObservers() {
        viewModel.selectedProduct.observe(
            viewLifecycleOwner,
            {
                val fretsNumber = it?.fretsNumber ?: 0
                binding?.productDetailsFrets?.titleString =
                    getString(R.string.product_details_frets_title, fretsNumber)
            }
        )

        viewModel.selectedBodyColor.observe(
            viewLifecycleOwner,
            {
                val shape = viewModel.selectedBodyShape.value
                if (it != null && shape != null) {
                    binding?.productDetailsImage?.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), getProductDrawable(it, shape))
                    )
                    binding?.productDetailsImage?.contentDescription =
                        context?.getString(getProductContentDescription(it, shape))
                }
            }
        )
    }

    private fun setupListeners() {
        binding?.productDetailsCustomizePlaceOrder?.setOnClickListener {
            (it as LottieAnimationView).apply {
                if (!isAnimating) {
                    playAnimation()
                    viewModel.updateOrder()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
