/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.customize

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat.makeSceneTransitionAnimation
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.ActivityProductCustomizeBinding
import com.microsoft.device.display.sampleheroapp.presentation.devmode.DevModeActivity
import com.microsoft.device.display.sampleheroapp.presentation.devmode.DevModeViewModel
import com.microsoft.device.display.sampleheroapp.presentation.product.customize.ProductCustomizeViewModel.Companion.SELECTED_DEFAULT_VALUE
import com.microsoft.device.display.sampleheroapp.presentation.product.customize.ProductCustomizeViewModel.Companion.SELECTED_PRODUCT_ID
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.getTopCenterPoint
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialBalloon
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialBalloonType
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialViewModel
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductCustomizeActivity : AppCompatActivity(), ScreenInfoListener {

    private val viewModel: ProductCustomizeViewModel by viewModels()
    private val tutorialViewModel: TutorialViewModel by viewModels()
    private val rotationViewModel: RotationViewModel by viewModels()

    @Inject lateinit var tutorial: TutorialBalloon

    private lateinit var binding: ActivityProductCustomizeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductCustomizeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getLongExtra(SELECTED_PRODUCT_ID, SELECTED_DEFAULT_VALUE)
            .takeIf { it != SELECTED_DEFAULT_VALUE }
            ?.let { viewModel.initSelectedProduct(it) }
        setupToolbar()
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
        tutorial.hide()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showDeveloperModeTutorial(anchorView: View) {
        if (tutorialViewModel.shouldShowDeveloperModeTutorial()) {
            tutorial.show(anchorView, TutorialBalloonType.DEVELOPER_MODE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (rotationViewModel.isDualMode.value == true) {
            menuInflater.inflate(R.menu.main_menu, menu)
            menu?.findItem(R.id.menu_main_dev_mode)?.actionView?.apply {
                showDeveloperModeTutorial(this)
                setOnClickListener {
                    onDevModeClicked(it)
                }
            }
        }
        return true
    }

    private fun onDevModeClicked(view: View) {
        val options = makeSceneTransitionAnimation(this, view, DevModeActivity.SHARED_ELEMENT_NAME)

        Intent(this, DevModeActivity::class.java).apply {
            val viewCenter = view.getTopCenterPoint()

            putExtra(DevModeActivity.EXTRA_ANIMATION_X, viewCenter.x)
            putExtra(DevModeActivity.EXTRA_ANIMATION_Y, viewCenter.y)
            putExtra(DevModeActivity.EXTRA_APP_SCREEN, DevModeViewModel.AppScreen.PRODUCTS_CUSTOMIZE.path)
            putExtra(DevModeActivity.EXTRA_DESIGN_PATTERN, DevModeViewModel.DesignPattern.COMPANION_PANE.path)
            putExtra(DevModeActivity.EXTRA_SDK_COMPONENT, DevModeViewModel.SdkComponent.SURFACE_DUO_LAYOUT.path)

            startActivity(this, options.toBundle())
        }
        tutorialViewModel.onDeveloperModeOpen()
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
