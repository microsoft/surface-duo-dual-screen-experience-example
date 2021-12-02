/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.catalog

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogItem
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage.Page1
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage.Page2
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage.Page3
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage.Page4
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage.Page5
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage.Page6
import com.microsoft.device.samples.dualscreenexperience.domain.catalog.model.CatalogPage.Page7
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.item.CatalogPage1Fragment
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.item.CatalogPage2Fragment
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.item.CatalogPage3Fragment
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.item.CatalogPage4Fragment
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.item.CatalogPage5Fragment
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.item.CatalogPage6Fragment
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.item.CatalogPage7Fragment
import com.microsoft.device.samples.dualscreenexperience.presentation.util.DataListProvider

internal class CatalogListAdapter(
    fragmentManager: FragmentManager,
    private val dataProvider: DataListProvider<CatalogItem>
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    var showTwoPages = false

    override fun getCount(): Int = dataProvider.getDataList()?.size ?: 0

    override fun getItem(position: Int): Fragment {
        dataProvider.getDataList()?.get(position)?.let { item ->
            return when (item.viewType) {
                Page1 -> CatalogPage1Fragment()
                Page2 -> CatalogPage2Fragment()
                Page3 -> CatalogPage3Fragment()
                Page4 -> CatalogPage4Fragment()
                Page5 -> CatalogPage5Fragment()
                Page6 -> CatalogPage6Fragment()
                Page7 -> CatalogPage7Fragment()
            }
        }
        throw RuntimeException("Catalog Type Error")
    }

    fun refreshData() {
        notifyDataSetChanged()
    }

    override fun getPageWidth(position: Int): Float {
        // 0.5f : Each page occupies full space
        // 1.0f : Each page occupies half space
        return if (showTwoPages) 0.5f else 1.0f
    }
}
