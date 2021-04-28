/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.customize

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.presentation.product.customize.ProductCustomizeViewModel.Companion.SELECTED_DEFAULT_VALUE
import com.microsoft.device.display.sampleheroapp.presentation.product.customize.ProductCustomizeViewModel.Companion.SELECTED_PRODUCT_ID
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialViewModel
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductCustomizeActivity : AppCompatActivity(), ScreenInfoListener {

    private val viewModel: ProductCustomizeViewModel by viewModels()
    private val tutorialViewModel: TutorialViewModel by viewModels()
    private val rotationViewModel: RotationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_customize)
        intent.getLongExtra(SELECTED_PRODUCT_ID, SELECTED_DEFAULT_VALUE)
            .takeIf { it != SELECTED_DEFAULT_VALUE }
            ?.let { viewModel.initSelectedProduct(it) }
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        rotationViewModel.currentRotation.value = screenInfo.getScreenRotation()
        rotationViewModel.isDualMode.value = screenInfo.isDualMode()

        if (screenInfo.isDualMode()) {
            setupDualScreenFragments()
            tutorialViewModel.onDualMode()
        } else {
            setupSingleScreenFragment()
        }
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
    }

    private fun setupSingleScreenFragment() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_SINGLE_SCREEN) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.first_container_id, ProductCustomizeFragment(), FRAGMENT_SINGLE_SCREEN)
                .commit()
        }
    }

    private fun setupDualScreenFragments() {
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_START) == null &&
            supportFragmentManager.findFragmentByTag(FRAGMENT_DUAL_END) == null
        ) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.first_container_id,
                    ProductCustomizeFragment(),
                    FRAGMENT_DUAL_START
                )
                .commit()

            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.second_container_id,
                    ProductCustomizeDetailsFragment(),
                    FRAGMENT_DUAL_END
                )
                .commit()
        }
    }

    companion object {
        private const val FRAGMENT_DUAL_START = "ProductCustomizeFragment"
        private const val FRAGMENT_DUAL_END = "ProductCustomizeDetailsFragment"
        private const val FRAGMENT_SINGLE_SCREEN = "ProductCustomizeFragmentSingle"
    }
}
