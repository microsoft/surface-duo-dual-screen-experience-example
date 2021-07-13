/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.catalog

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.microsoft.device.display.sampleheroapp.domain.catalog.model.CatalogItem
import com.microsoft.device.display.sampleheroapp.presentation.product.catalog.item.CatalogItemFragment
import com.microsoft.device.display.sampleheroapp.presentation.util.DataListProvider

internal class CatalogListAdapter(
    fragmentManager: FragmentManager,
    private val dataProvider: DataListProvider<CatalogItem>
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var showTwoPages = false

    override fun getCount(): Int = dataProvider.getDataList()?.size ?: 0

    override fun getItem(position: Int): Fragment {
        val size = dataProvider.getDataList()?.size ?: 0
        dataProvider.getDataList()?.get(position)?.let { item ->
            return CatalogItemFragment.createInstance(
                item,
                position,
                size,
                item.viewType
            )
        }
        throw RuntimeException("Catalog Type Error")
    }

    fun refreshData() {
        notifyDataSetChanged()
    }

    override fun getPageWidth(position: Int): Float {
        // 0.5f : Each pages occupy full space
        // 1.0f : Each pages occupy half space
        return if (showTwoPages) 0.5f else 1.0f
    }
}
