/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentCatalogItemPage1Binding
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentCatalogItemPage2Binding
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentCatalogItemPage3Binding
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentCatalogItemPage4Binding
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentCatalogItemPage5Binding
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentCatalogItemPage6Binding
import com.microsoft.device.samples.dualscreenexperience.databinding.FragmentCatalogItemPage7Binding
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogViewType
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.CatalogListViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LockableNestedScrollView

class CatalogItemFragment : Fragment() {

    private val catalogItem: CatalogItem?
        get() {
            return arguments?.getParcelable(KEY_ITEM_ID) as CatalogItem?
        }

    private val viewType: CatalogViewType
        get() {
            return arguments?.get(KEY_VIEW_TYPE) as CatalogViewType
        }

    private val pageNumber: String
        get() {
            return requireActivity().getString(
                R.string.catalog_page_no,
                (arguments?.getInt(KEY_PAGE_NO) ?: 0) + 1,
                arguments?.getInt(KEY_TOTAL_PAGES)
            )
        }

    private val viewModel: CatalogListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = inflateByViewType(inflater, container)
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun inflateByViewType(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding =
        when (viewType) {
            CatalogViewType.Layout1 ->
                FragmentCatalogItemPage1Binding
                    .inflate(inflater, container, false)
                    .apply {
                        catalogItem = this@CatalogItemFragment.catalogItem
                        pageNumber = this@CatalogItemFragment.pageNumber
                    }
            CatalogViewType.Layout2 ->
                FragmentCatalogItemPage2Binding
                    .inflate(inflater, container, false)
                    .apply {
                        catalogItem = this@CatalogItemFragment.catalogItem
                        pageNumber = this@CatalogItemFragment.pageNumber
                    }
            CatalogViewType.Layout3 ->
                FragmentCatalogItemPage3Binding
                    .inflate(inflater, container, false)
                    .apply {
                        catalogItem = this@CatalogItemFragment.catalogItem
                        pageNumber = this@CatalogItemFragment.pageNumber
                    }

            CatalogViewType.Layout4 ->
                FragmentCatalogItemPage4Binding
                    .inflate(inflater, container, false)
                    .apply {
                        catalogItem = this@CatalogItemFragment.catalogItem
                        pageNumber = this@CatalogItemFragment.pageNumber
                    }
            CatalogViewType.Layout5 ->
                FragmentCatalogItemPage5Binding
                    .inflate(inflater, container, false)
                    .apply {
                        catalogItem = this@CatalogItemFragment.catalogItem
                        pageNumber = this@CatalogItemFragment.pageNumber
                    }
            CatalogViewType.Layout6 ->
                FragmentCatalogItemPage6Binding
                    .inflate(inflater, container, false)
                    .apply {
                        catalogItem = this@CatalogItemFragment.catalogItem
                        pageNumber = this@CatalogItemFragment.pageNumber
                    }
            CatalogViewType.Layout7 ->
                FragmentCatalogItemPage7Binding
                    .inflate(inflater, container, false)
                    .apply {
                        catalogItem = this@CatalogItemFragment.catalogItem
                        pageNumber = this@CatalogItemFragment.pageNumber
                    }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupScroll(view)
    }

    private fun setupScroll(view: View) {
        viewModel.isScrollingEnabled.observe(
            viewLifecycleOwner,
            { isScrollingEnabled ->
                view.findViewById<LockableNestedScrollView>(R.id.catalog_item_scroll_view)
                    ?.isScrollingEnabled = isScrollingEnabled
            }
        )
    }

    companion object {
        const val KEY_ITEM_ID = "key_item_id"
        const val KEY_PAGE_NO = "key_page_no"
        const val KEY_TOTAL_PAGES = "key_total_pages"
        const val KEY_VIEW_TYPE = "key_view_type"

        fun createInstance(item: CatalogItem, pageNo: Int, totalPages: Int, viewType: CatalogViewType) =
            CatalogItemFragment().apply {
                arguments = bundleOf(
                    KEY_ITEM_ID to item,
                    KEY_PAGE_NO to pageNo,
                    KEY_TOTAL_PAGES to totalPages,
                    KEY_VIEW_TYPE to viewType
                )
            }
    }
}
