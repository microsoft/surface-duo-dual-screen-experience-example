/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.product.customize

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.children
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
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
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentProductCustomizeBinding
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.GuitarType
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductColor
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductType
import com.microsoft.device.samples.dualscreenexperience.presentation.product.ProductViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.CustomizeCardView
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductContentDescription
import com.microsoft.device.samples.dualscreenexperience.presentation.product.util.getProductDrawable
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel.Companion.ROTATE_HORIZONTALLY
import com.microsoft.device.samples.dualscreenexperience.presentation.util.appCompatActivity
import com.microsoft.device.samples.dualscreenexperience.presentation.util.changeToolbarTitle
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isFragmentInLandscape
import com.microsoft.device.samples.dualscreenexperience.presentation.util.rotate
import com.microsoft.device.samples.dualscreenexperience.presentation.util.setupToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductCustomizeFragment : Fragment() {

    private val productViewModel: ProductViewModel by activityViewModels()
    private val viewModel: ProductCustomizeViewModel by activityViewModels()
    private val layoutInfoViewModel: LayoutInfoViewModel by activityViewModels()

    private var binding: FragmentProductCustomizeBinding? = null

    private lateinit var windowInfoRepository: WindowInfoRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductCustomizeBinding.inflate(inflater, container, false)
        binding?.viewModel = viewModel
        binding?.isDualMode = layoutInfoViewModel.isDualMode.value
        binding?.isScreenInLandscape = false
        binding?.lifecycleOwner = this
        return binding?.root
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    private fun setupToolbar() {
        appCompatActivity?.changeToolbarTitle(getString(R.string.nav_products_title))
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
                    if (viewModel.selectedGuitarType.value == null) {
                        viewModel.selectedGuitarType.value = it.guitarType
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

                        resetColorViews(binding?.productCustomizeColorContainer, it.colorList, newColor)
                    }
                }
            }
        )

        viewModel.selectedBodyColor.observe(
            viewLifecycleOwner,
            {
                val shape = viewModel.selectedBodyShape.value
                val guitarType = viewModel.selectedGuitarType.value
                if (it != null && shape != null && guitarType != null) {
                    setCustomizeImageDrawable(it, shape, guitarType)

                    val visibleColorCount =
                        binding?.productCustomizeColorContainer?.children
                            ?.filter { colorView -> colorView.isVisible }?.count()

                    if (shape.colorList.size != visibleColorCount) {
                        resetColorViews(binding?.productCustomizeColorContainer, shape.colorList, it)
                    }
                }
            }
        )

        viewModel.selectedGuitarType.observe(
            viewLifecycleOwner,
            {
                val color = viewModel.selectedBodyColor.value
                val shape = viewModel.selectedBodyShape.value
                if (it != null && color != null && shape != null) {
                    setCustomizeImageDrawable(color, shape, it)
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

        binding?.productCustomizeTypeToggle?.setOnCheckedChangeListener { radioGroup, itemId ->
            when (itemId) {
                R.id.product_customize_type_bass -> viewModel.selectedGuitarType.value = GuitarType.BASS
                R.id.product_customize_type_normal -> viewModel.selectedGuitarType.value = GuitarType.NORMAL
            }
        }
    }

    private fun setCustomizeImageDrawable(color: ProductColor, shape: ProductType, guitarType: GuitarType) {
        binding?.productCustomizeImage?.setImageBitmap(
            ContextCompat.getDrawable(
                requireContext(),
                getProductDrawable(color, shape, guitarType)
            )?.toBitmap()?.rotate(ROTATE_HORIZONTALLY)
        )
        binding?.productCustomizeImage?.contentDescription =
            context?.getString(getProductContentDescription(color, shape, guitarType))

        binding?.productCustomizeImageLandscape?.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                getProductDrawable(color, shape, guitarType)
            )
        )

        binding?.productCustomizeImageLandscape?.contentDescription =
            context?.getString(getProductContentDescription(color, shape, guitarType))
    }

    private fun resetColorViews(parentView: ViewGroup?, colorList: List<ProductColor>, defaultSelected: ProductColor) {
        parentView?.post {
            for (colorIndex in 1..5) {
                val colorView = getColorView(colorIndex)
                if (colorIndex > colorList.size) {
                    colorView?.isInvisible = true
                } else {
                    colorView?.isVisible = true
                    val currentColor = colorList[colorIndex - 1]
                    colorView?.apply {
                        productColor = currentColor
                        setOnClickListener {
                            if (!isSelected) {
                                select()
                                viewModel.selectedBodyColor.value = currentColor
                                colorList.indices
                                    .filter { colorIndex != it }
                                    .forEach { getColorView(it)?.unselect() }
                            }
                        }
                    }
                    if (currentColor == defaultSelected) {
                        colorView?.select()
                    }
                }
            }
        }
    }

    private fun getColorView(index: Int) =
        when (index) {
            1 -> binding?.productCustomizeColor1
            2 -> binding?.productCustomizeColor2
            3 -> binding?.productCustomizeColor3
            4 -> binding?.productCustomizeColor4
            5 -> binding?.productCustomizeColor5
            else -> null
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
            ProductType.ROCK -> binding?.productCustomizeBody1
            ProductType.CLASSIC -> binding?.productCustomizeBody2
            ProductType.ELECTRIC -> binding?.productCustomizeBody3
            ProductType.HARDROCK -> binding?.productCustomizeBody4
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
