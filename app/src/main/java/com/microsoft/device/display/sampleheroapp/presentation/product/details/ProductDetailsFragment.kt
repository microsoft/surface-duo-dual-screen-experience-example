/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.airbnb.lottie.LottieAnimationView
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentProductDetailsBinding
import com.microsoft.device.display.sampleheroapp.presentation.product.ProductViewModel
import com.microsoft.device.display.sampleheroapp.presentation.product.customize.ProductCustomizeViewModel
import com.microsoft.device.display.sampleheroapp.presentation.product.util.getProductContentDescription
import com.microsoft.device.display.sampleheroapp.presentation.product.util.getProductDrawable
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.appCompatActivity
import com.microsoft.device.display.sampleheroapp.presentation.util.changeToolbarTitle
import com.microsoft.device.display.sampleheroapp.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private val viewModel: ProductViewModel by activityViewModels()
    private val customizeViewModel: ProductCustomizeViewModel by activityViewModels()
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
        initBindingFields()
        initSelectedProductImage()
        setupObservers()
        setupCustomizeObservers()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        if (rotationViewModel.isDualMode.value == false) {
            setupToolbar()
        } else {
            appCompatActivity?.setupToolbar(isBackButtonEnabled = false) {}
        }
    }

    private fun setupToolbar() {
        appCompatActivity?.changeToolbarTitle(getString(R.string.app_name))
        appCompatActivity?.setupToolbar(isBackButtonEnabled = true) {
            viewModel.navigateUp()
            appCompatActivity?.setupToolbar(isBackButtonEnabled = false) {}
        }
    }

    private fun initBindingFields() {
        binding?.productDetailsFrets?.descriptionString =
            getString(R.string.product_details_frets_description)

        binding?.productDetailsPickup?.titleString =
            getString(R.string.product_details_pickup_title)

        binding?.productDetailsPickup?.descriptionString =
            getString(R.string.product_details_pickup_description)
    }

    private fun initSelectedProductImage() {
        binding?.productDetailsImage?.apply {
            viewModel.selectedProduct.value?.let {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        getProductDrawable(it.color, it.bodyShape)
                    )
                )
            }
        }
    }

    private fun setupListeners() {
        binding?.productDetailsCustomizeButton?.setOnClickListener {
            viewModel.selectedProduct.value?.productId?.let {
                customizeViewModel.initCustomizedProduct(it)
                viewModel.navigateToCustomize()
            }
        }

        binding?.productDetailsCustomizePlaceOrder?.setOnClickListener {
            (it as LottieAnimationView).apply {
                if (!isAnimating) {
                    playAnimation()
                    customizeViewModel.updateOrder()
                }
            }
        }
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

        viewModel.productList.observe(viewLifecycleOwner, { viewModel.selectFirstProduct() })
    }

    private fun setupCustomizeObservers() {
        customizeViewModel.customizedProduct.observe(
            viewLifecycleOwner,
            {
                if (it == null) {
                    initSelectedProductImage()
                    binding?.isInCustomizeMode = false

                    if (rotationViewModel.isDualMode.value == true) {
                        appCompatActivity?.setupToolbar(isBackButtonEnabled = false) {}
                    } else {
                        setupToolbar()
                    }
                } else {
                    binding?.isInCustomizeMode = true
                }
            }
        )

        customizeViewModel.selectedBodyColor.observe(
            viewLifecycleOwner,
            {
                val shape = customizeViewModel.selectedBodyShape.value
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
