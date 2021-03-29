/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.DuoNavigation
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.ActivityMainBinding
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialViewModel
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ScreenInfoListener {

    @Inject lateinit var navigator: AppNavigator

    private val tutorialViewModel: TutorialViewModel by viewModels()
    private val rotationViewModel: RotationViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
        navigator.bind(DuoNavigation.findNavController(this, R.id.nav_host_fragment))

        // binding.bottomNavView.setupWithNavController(findNavController(this, R.id.nav_host_fragment))
        binding.bottomNavView.arrangeButtons(3, 0)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
        navigator.unbind()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (isNavigationAtStart()) {
            finish()
        }
        super.onBackPressed()
    }

    private fun isNavigationAtStart() =
        DuoNavigation.findNavController(
            this,
            R.id.nav_host_fragment
        ).currentDestination?.id == R.id.fragment_store_map

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        rotationViewModel.currentRotation.value = screenInfo.getScreenRotation()
        if (screenInfo.isDualMode()) {
            tutorialViewModel.onDualMode()
        }
    }
}
