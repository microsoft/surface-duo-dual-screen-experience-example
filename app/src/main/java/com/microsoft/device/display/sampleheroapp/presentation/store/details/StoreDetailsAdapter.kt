/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store.details

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class StoreDetailsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = TAB_COUNT

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> StoreDetailsAboutFragment()
            1 -> StoreDetailsContactFragment()
            else -> StoreDetailsAboutFragment()
        }

    companion object {
        const val TAB_COUNT = 2
    }
}
