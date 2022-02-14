/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.product.details

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import androidx.window.layout.WindowLayoutInfo
import com.airbnb.lottie.LottieAnimationView
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentProductDetailsBinding
import com.microsoft.device.samples.dualscreenexperience.presentation.product.ProductViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.product.customize.ProductCustomizeViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductContentDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductDrawable
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isFragmentInLandscape
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private val viewModel: ProductViewModel by activityViewModels()
    private val customizeViewModel: ProductCustomizeViewModel by activityViewModels()
    private val layoutInfoViewModel: LayoutInfoViewModel by activityViewModels()

    private var binding: FragmentProductDetailsBinding? = null

    private lateinit var windowInfoRepository: WindowInfoRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        binding?.viewModel = viewModel
        binding?.isScreenInLandscape = false
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        observeWindowLayoutInfo(context as AppCompatActivity)
    }

    private fun observeWindowLayoutInfo(activity: AppCompatActivity) {
        windowInfoRepository = activity.windowInfoRepository()
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoRepository.windowLayoutInfo.collect {
                    onWindowLayoutInfoChanged(it)
                }
            }
        }
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        binding?.isScreenInLandscape = requireActivity().isFragmentInLandscape(windowLayoutInfo)
    }

    override fun onResume() {
        super.onResume()
        if (layoutInfoViewModel.isDualMode.value == false) {
            setupToolbar()
        } else {
            appCompatActivity?.setupToolbar(isBackButtonEnabled = false) {}
        }
    }

    private fun setupToolbar() {
        appCompatActivity?.changeToolbarTitle(getString(R.string.nav_products_title))
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
        viewModel.selectedProduct.observe(viewLifecycleOwner) {
            val fretsNumber = it?.fretsNumber ?: 0
            binding?.productDetailsFrets?.titleString =
                getString(R.string.product_details_frets_title, fretsNumber)
            binding?.productDetailsTypeDescription?.text =
                SpannableStringBuilder()
                    .append(getString(R.string.product_details_type_description, it?.name))
                    .append(" ")
                    .bold { append(getString(R.string.product_details_type_description_bold, it?.deliveryDays)) }
        }

        viewModel.productList.observe(viewLifecycleOwner) { viewModel.selectFirstProduct() }
    }

    private fun setupCustomizeObservers() {
        customizeViewModel.customizedProduct.observe(viewLifecycleOwner) {
            if (it == null) {
                initSelectedProductImage()
                binding?.isInCustomizeMode = false

                if (layoutInfoViewModel.isDualMode.value == true) {
                    appCompatActivity?.setupToolbar(isBackButtonEnabled = false) {}
                } else {
                    setupToolbar()
                }
            } else {
                binding?.isInCustomizeMode = true
            }
        }

        customizeViewModel.selectedBodyColor.observe(viewLifecycleOwner) {
            val shape = customizeViewModel.selectedBodyShape.value
            if (it != null && shape != null) {
                binding?.productDetailsImage?.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), getProductDrawable(it, shape))
                )
                binding?.productDetailsImage?.contentDescription =
                    context?.getString(getProductContentDescription(it, shape))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
