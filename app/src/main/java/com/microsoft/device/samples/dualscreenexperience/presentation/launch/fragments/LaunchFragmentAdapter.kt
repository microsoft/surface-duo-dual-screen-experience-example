/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.launch.fragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LaunchFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = FRAGMENTS_COUNT

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> LaunchTitleFragment()
            1 -> LaunchDescriptionFragment()
            else -> LaunchTitleFragment()
        }

    companion object {
        const val FRAGMENTS_COUNT = 2
    }
}
