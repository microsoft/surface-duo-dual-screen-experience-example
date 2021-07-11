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
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.airbnb.lottie.LottieAnimationView
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.FragmentProductCustomizeBinding
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductColor
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductType
import com.microsoft.device.display.sampleheroapp.presentation.product.ProductViewModel
import com.microsoft.device.display.sampleheroapp.presentation.product.util.CustomizeCardView
import com.microsoft.device.display.sampleheroapp.presentation.product.util.getProductContentDescription
import com.microsoft.device.display.sampleheroapp.presentation.product.util.getProductDrawable
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel.Companion.ROTATE_HORIZONTALLY
import com.microsoft.device.display.sampleheroapp.presentation.util.appCompatActivity
import com.microsoft.device.display.sampleheroapp.presentation.util.changeToolbarTitle
import com.microsoft.device.display.sampleheroapp.presentation.util.rotate
import com.microsoft.device.display.sampleheroapp.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductCustomizeFragment : Fragment() {

    private val productViewModel: ProductViewModel by activityViewModels()
    private val viewModel: ProductCustomizeViewModel by activityViewModels()
    private val rotationViewModel: RotationViewModel by activityViewModels()

    private var binding: FragmentProductCustomizeBinding? = null

    private var colorViewList: ArrayList<CustomizeCardView>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductCustomizeBinding.inflate(inflater, container, false)
        binding?.viewModel = viewModel
        binding?.rotationViewModel = rotationViewModel
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initColorViewList()

        setupObservers()
        setupListeners()
    }

    private fun initColorViewList() {
        colorViewList = arrayListOf()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        appCompatActivity?.supportActionBar?.show()
        appCompatActivity?.changeToolbarTitle(getString(R.string.app_name))
        appCompatActivity?.setupToolbar(isBackButtonEnabled = true) {
            viewModel.reset()
            productViewModel.navigateUp()
        }
    }

    private fun setupObservers() {
        viewModel.customizedProduct.observe(
            viewLifecycleOwner,
            { product ->
                product?.let {
                    if (viewModel.selectedBodyShape.value == null) {
                        viewModel.selectedBodyShape.value = it.bodyShape
                    }
                    viewModel.selectedBodyShape.value?.let { updatedBodyShape ->
                        getBodyShape(updatedBodyShape)?.select()
                    }
                }
            }
        )

        viewModel.selectedBodyShape.observe(
            viewLifecycleOwner,
            {
                val selectedItem = viewModel.customizedProduct.value
                if (it != null && selectedItem != null) {
                    if (it == selectedItem.bodyShape) {
                        selectedItem.color
                    } else {
                        it.colorList[0]
                    }.let { newColor ->
                        viewModel.selectedBodyColor.value = newColor
                        addColorViews(binding?.productCustomizeColorContainer, it.colorList, newColor)
                    }
                }
            }
        )

        viewModel.selectedBodyColor.observe(
            viewLifecycleOwner,
            {
                val shape = viewModel.selectedBodyShape.value
                if (it != null && shape != null) {
                    setCustomizeImageDrawable(it, shape)

                    colorViewList?.let { colorViews ->
                        if (colorViews.isEmpty()) {
                            addColorViews(binding?.productCustomizeColorContainer, shape.colorList, it)
                        }
                    }
                }
            }
        )
    }

    private fun setupListeners() {
        binding?.productCustomizeBody1?.setOnClickListener(::onBodyShapeClicked)
        binding?.productCustomizeBody2?.setOnClickListener(::onBodyShapeClicked)
        binding?.productCustomizeBody3?.setOnClickListener(::onBodyShapeClicked)
        binding?.productCustomizeBody4?.setOnClickListener(::onBodyShapeClicked)

        binding?.productCustomizePlaceOrderButton?.setOnClickListener {
            (it as LottieAnimationView).apply {
                if (!isAnimating) {
                    playAnimation()
                    viewModel.updateOrder()
                }
            }
        }
    }

    private fun setCustomizeImageDrawable(color: ProductColor, shape: ProductType) {
        binding?.productCustomizeImage?.setImageBitmap(
            ContextCompat.getDrawable(
                requireContext(),
                getProductDrawable(color, shape)
            )?.toBitmap()?.rotate(ROTATE_HORIZONTALLY)
        )
        binding?.productCustomizeImage?.contentDescription =
            context?.getString(getProductContentDescription(color, shape))

        binding?.productCustomizeImageLandscape?.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                getProductDrawable(color, shape)
            )
        )

        binding?.productCustomizeImageLandscape?.contentDescription =
            context?.getString(getProductContentDescription(color, shape))
    }

    private fun addColorViews(parentView: ViewGroup?, colorList: List<ProductColor>, defaultSelected: ProductColor) {
        parentView?.removeAllViews()
        colorViewList?.clear()
        for (color in colorList) {
            val colorView = CustomizeCardView(
                requireContext()
            ).apply {
                productColor = color
                setOnClickListener {
                    if (!isSelected) {
                        select()
                        viewModel.selectedBodyColor.value = color
                        colorViewList?.filter { it.productColor != color }?.forEach { it.unselect() }
                    }
                }
            }
            if (color == defaultSelected) {
                colorView.select()
            }
            colorViewList?.add(colorView)
            parentView?.addView(colorView)
        }
    }

    private fun onBodyShapeClicked(view: View) {
        if (view is CustomizeCardView && !view.isSelected) {
            view.select()
            viewModel.selectedBodyShape.value = view.productType
            ProductType.values().filter { view.productType != it }.forEach { getBodyShape(it)?.unselect() }
        }
    }

    private fun getBodyShape(type: ProductType) =
        when (type) {
            ProductType.WARLOCK -> binding?.productCustomizeBody1
            ProductType.CLASSIC -> binding?.productCustomizeBody2
            ProductType.EXPLORER -> binding?.productCustomizeBody3
            ProductType.MUSICLANDER -> binding?.productCustomizeBody4
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        colorViewList = null
    }
}
