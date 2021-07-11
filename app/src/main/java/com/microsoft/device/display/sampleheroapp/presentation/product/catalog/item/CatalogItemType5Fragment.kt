/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.catalog.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.microsoft.device.display.sampleheroapp.databinding.FragmentCatalogItemType5Binding
import com.microsoft.device.display.sampleheroapp.domain.catalog.model.CatalogItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogItemType5Fragment : CatalogItemFragment() {

    companion object {
        fun createInstance(catalogItem: CatalogItem, pageNo: Int, totalPages: Int) =
            CatalogItemType5Fragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_ITEM_ID, catalogItem)
                    putInt(KEY_PAGE_NO, pageNo)
                    putInt(KEY_TOTAL_PAGES, totalPages)
                }
            }
    }

    private var binding: FragmentCatalogItemType5Binding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCatalogItemType5Binding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.catalogItem = catalogItem
        binding?.pageNumber = pageNumber
    }
}
