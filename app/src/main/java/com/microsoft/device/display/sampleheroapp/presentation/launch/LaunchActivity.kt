/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.launch

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.presentation.MainActivity
import com.microsoft.device.display.sampleheroapp.presentation.launch.fragments.LaunchDescriptionFragment
import com.microsoft.device.display.sampleheroapp.presentation.launch.fragments.LaunchTitleFragment
import com.microsoft.device.display.sampleheroapp.presentation.launch.fragments.SingleLaunchFragment
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : FragmentActivity(), ScreenInfoListener {

    private val viewModel: LaunchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.isLaunchButtonClicked.observe(
            this,
            {
                if (it) {
                    navigateToMainActivity()
                    viewModel.isLaunchButtonClicked.value = false
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        viewModel.isDualMode.value = screenInfo.isDualMode()
        if (screenInfo.isDualMode()) {
            setupDualScreenFragments()
        } else {
            setupSingleScreenFragments()
        }
    }

    private fun setupSingleScreenFragments() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_SINGLE_SCREEN) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.first_container_id, SingleLaunchFragment(), FRAGMENT_SINGLE_SCREEN)
                .commit()
        }
    }

    private fun setupDualScreenFragments() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_START) == null &&
            supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_END) == null
        ) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.first_container_id, LaunchTitleFragment(), FRAGMENT_DUAL_START)
                .commit()

            supportFragmentManager.beginTransaction()
                .replace(R.id.second_container_id, LaunchDescriptionFragment(), FRAGMENT_DUAL_END)
                .commit()
        }
    }

    companion object {
        private const val FRAGMENT_DUAL_START = "FragmentDualStart"
        private const val FRAGMENT_DUAL_END = "FragmentDualEnd"
        private const val FRAGMENT_SINGLE_SCREEN = "FragmentSingleScreen"
    }
}
