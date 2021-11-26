/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.item

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.window.layout.FoldingFeature
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentCatalogItemPage7Binding
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.CatalogListViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.getBoundsInWindow
import com.microsoft.device.samples.dualscreenexperience.presentation.util.hasExpandedWindowLayoutSize
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isFragmentWidthSmall
import com.microsoft.device.samples.dualscreenexperience.presentation.util.sizeOrZero

class CatalogPage7Fragment : Fragment() {

    private var binding: FragmentCatalogItemPage7Binding? = null

    private val layoutInfoViewModel: LayoutInfoViewModel by activityViewModels()
    private val catalogViewModel: CatalogListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatalogItemPage7Binding.inflate(inflater, container, false)
        binding?.catalogItem = catalogViewModel.catalogItemList.value?.get(CatalogPage.Page7.ordinal)
        binding?.pageNumber = resources.getString(
            R.string.catalog_page_no,
            CatalogPage.Page7.ordinal + 1,
            catalogViewModel.getDataList().sizeOrZero()
        )
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
    }

    private fun updateUI() {
        val showsTwoPages = catalogViewModel.showTwoPages.value == true
        val isDualMode = layoutInfoViewModel.isDualMode.value == true
        val currentFoldingFeature = layoutInfoViewModel.foldingFeature.value
        val isExpandedWindowSize = activity?.hasExpandedWindowLayoutSize() == true

        activity?.isFragmentWidthSmall(currentFoldingFeature, showsTwoPages)?.takeIf {
            it && !showsTwoPages
        }?.let {
            alignLayoutToSmallWindowWidth()
        }

        currentFoldingFeature?.takeIf {
            isDualMode && !showsTwoPages && it.orientation == FoldingFeature.Orientation.HORIZONTAL
        }?.let {
            alignLayoutToHorizontalFoldingFeature(it)

            if (isExpandedWindowSize) {
                updateText()
            }
        }
    }

    private fun updateText() {
        val text16sp = resources.getDimension(R.dimen.text_size_16)
        binding?.textFirst?.setTextSize(TypedValue.COMPLEX_UNIT_PX, text16sp)
        binding?.textSecond?.setTextSize(TypedValue.COMPLEX_UNIT_PX, text16sp)
    }

    private fun alignLayoutToSmallWindowWidth() {
        val constraintLayout = binding?.catalogItem7Layout ?: return
        val set = ConstraintSet()
        set.clone(constraintLayout)

        val margin = resources.getDimension(R.dimen.normal_margin).toInt()
        val marginHorizontal = resources.getDimension(R.dimen.catalog_horizontal_margin).toInt()

        set.connect(
            R.id.image_first, ConstraintSet.END,
            R.id.catalog_item7_layout, ConstraintSet.END, marginHorizontal
        )
        set.connect(
            R.id.image_first, ConstraintSet.BOTTOM,
            R.id.text_first, ConstraintSet.TOP, margin
        )

        set.connect(
            R.id.text_first, ConstraintSet.TOP,
            R.id.image_first, ConstraintSet.BOTTOM, margin
        )
        set.connect(
            R.id.text_first, ConstraintSet.START,
            R.id.catalog_item7_layout, ConstraintSet.START, marginHorizontal
        )

        set.connect(
            R.id.image_second, ConstraintSet.TOP,
            R.id.text_first, ConstraintSet.BOTTOM, margin
        )
        set.connect(
            R.id.image_second, ConstraintSet.BOTTOM,
            R.id.text_second, ConstraintSet.TOP, margin
        )
        set.connect(
            R.id.image_second, ConstraintSet.START,
            R.id.catalog_item7_layout, ConstraintSet.START, marginHorizontal
        )

        set.connect(
            R.id.text_second, ConstraintSet.TOP,
            R.id.image_second, ConstraintSet.BOTTOM, margin
        )

        set.connect(
            R.id.text_second, ConstraintSet.END,
            R.id.catalog_item7_layout, ConstraintSet.END, marginHorizontal
        )

        set.applyTo(constraintLayout)
    }

    private fun alignLayoutToHorizontalFoldingFeature(foldingFeature: FoldingFeature) {
        val constraintLayout = binding?.catalogItem7Layout ?: return

        foldingFeature.getBoundsInWindow(binding?.root)?.let { rect ->
            val horizontalFoldingFeatureHeight = rect.height().coerceAtLeast(1)

            val set = ConstraintSet()
            set.clone(constraintLayout)
            set.constrainHeight(R.id.horizontal_fold, horizontalFoldingFeatureHeight)

            set.connect(
                R.id.horizontal_fold, ConstraintSet.START,
                ConstraintSet.PARENT_ID, ConstraintSet.START, 0
            )
            set.connect(
                R.id.horizontal_fold, ConstraintSet.TOP,
                ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0
            )

            val foldingMargin = resources.getDimension(R.dimen.small_margin).toInt()

            set.setMargin(R.id.horizontal_fold, ConstraintSet.TOP, rect.top)

            if (activity?.isFragmentWidthSmall(foldingFeature, false) == false) {
                set.connect(
                    R.id.image_first, ConstraintSet.BOTTOM,
                    R.id.horizontal_fold, ConstraintSet.TOP, foldingMargin
                )
                set.connect(
                    R.id.text_second, ConstraintSet.TOP,
                    R.id.horizontal_fold, ConstraintSet.BOTTOM, foldingMargin
                )
            }
            set.connect(
                R.id.image_second, ConstraintSet.TOP,
                R.id.horizontal_fold, ConstraintSet.BOTTOM, foldingMargin
            )
            set.connect(
                R.id.text_first, ConstraintSet.BOTTOM,
                R.id.horizontal_fold, ConstraintSet.TOP, foldingMargin
            )

            set.setVisibility(R.id.horizontal_fold, View.VISIBLE)
            set.applyTo(constraintLayout)
        }
    }
}
