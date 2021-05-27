/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.DuoNavigation
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.ActivityMainBinding
import com.microsoft.device.display.sampleheroapp.presentation.order.OrderViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialBalloon
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialBalloonType
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialViewModel
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ScreenInfoListener {

    @Inject lateinit var navigator: AppNavigator
    @Inject lateinit var tutorial: TutorialBalloon

    private val tutorialViewModel: TutorialViewModel by viewModels()
    private val rotationViewModel: RotationViewModel by viewModels()

    @VisibleForTesting
    private val orderViewModel: OrderViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)

        DuoNavigation.findNavController(this, R.id.nav_host_fragment).let {
            // binding.bottomNavView.setupWithNavController(it)
            navigator.bind(it)
            it.addOnDestinationChangedListener { _, surfaceDuoNavDestination, _ ->
                if (
                    surfaceDuoNavDestination.id != R.id.fragment_order &&
                    surfaceDuoNavDestination.id != R.id.fragment_order_receipt
                ) {
                    tutorialViewModel.onStoresOpen()
                    tutorial.hide()
                }
            }
        }
        binding.bottomNavView.arrangeButtons(3, 0)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
        navigator.unbind()
        tutorial.hide()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
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
        rotationViewModel.isDualMode.value = screenInfo.isDualMode()
        if (screenInfo.isDualMode()) {
            tutorialViewModel.onDualMode()
        }
    }

    private fun setupObservers() {
        tutorialViewModel.showStoresTutorial.observe(
            this,
            {
                if (it == true) {
                    showTutorial(TutorialBalloonType.STORES)
                }
            }
        )
    }

    private fun showTutorial(type: TutorialBalloonType) {
        when (type) {
            TutorialBalloonType.STORES -> {
                val storeItem = findViewById<BottomNavigationItemView>(R.id.navigation_stores_graph)
                tutorial.show(window.decorView, TutorialBalloonType.STORES, storeItem)
            }
            else -> {}
        }
    }

    @VisibleForTesting
    fun getItemListLiveData() = orderViewModel.itemList

    @VisibleForTesting
    fun getSubmittedOrderLiveData() = orderViewModel.submittedOrder
}
