/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.host

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.microsoft.device.display.sampleheroapp.presentation.product.catalog.CatalogListFragment
import com.microsoft.device.display.sampleheroapp.presentation.product.list.ProductListFragment

class HostProductsFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = FRAGMENTS_COUNT

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> CatalogListFragment()
            1 -> ProductListFragment()
            else -> CatalogListFragment()
        }

    companion object {
        const val FRAGMENTS_COUNT = 2
    }
}
