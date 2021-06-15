/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.devmode

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewAnimationUtils.createCircularReveal
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnNextLayout
import androidx.navigation.SurfaceDuoNavigation
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.databinding.ActivityDevModeBinding
import com.microsoft.device.display.sampleheroapp.presentation.util.RotationViewModel
import com.microsoft.device.display.sampleheroapp.presentation.util.getTopCenterPoint
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.max

@AndroidEntryPoint
class DevModeActivity : AppCompatActivity(), ScreenInfoListener {

    private val rotationViewModel: RotationViewModel by viewModels()
    private val viewModel: DevModeViewModel by viewModels()

    @Inject lateinit var navigator: DevModeNavigator
    private lateinit var binding: ActivityDevModeBinding

    private var revealX: Int = 0
    private var revealY: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDevModeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()

        extractIntentUrlExtras(intent)

        if (savedInstanceState == null && extractIntentAnimationExtras(intent)) {
            binding.devRootLayout.visibility = INVISIBLE

            binding.devRootLayout.doOnNextLayout {
                revealActivity(revealX, revealY)
            }
        }
    }

    private fun extractIntentUrlExtras(intent: Intent) {
        intent.getStringExtra(EXTRA_DESIGN_PATTERN)?.let {
            viewModel.designPattern = DevModeViewModel.DesignPattern.get(it)
        }

        intent.getStringExtra(EXTRA_APP_SCREEN)?.let {
            viewModel.appScreen = DevModeViewModel.AppScreen.get(it)
        }

        intent.getStringExtra(EXTRA_SDK_COMPONENT)?.let {
            viewModel.sdkComponent = DevModeViewModel.SdkComponent.get(it)
        }
    }

    private fun extractIntentAnimationExtras(intent: Intent): Boolean {
        if (intent.hasExtra(EXTRA_ANIMATION_X) && intent.hasExtra(EXTRA_ANIMATION_Y)) {
            revealX = intent.getIntExtra(EXTRA_ANIMATION_X, 0)
            revealY = intent.getIntExtra(EXTRA_ANIMATION_Y, 0)
            return true
        }
        return false
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun revealActivity(x: Int, y: Int) {
        val circularReveal = createCircularReveal(binding.devRootLayout, x, y, START_RADIUS, getFinalRadius())
        circularReveal.duration = ANIMATION_DURATION

        binding.devRootLayout.visibility = VISIBLE
        circularReveal.start()
    }

    private fun unRevealActivity(view: View) {
        val viewCenter = view.getTopCenterPoint()

        createCircularReveal(binding.devRootLayout, viewCenter.x, viewCenter.y, getFinalRadius(), START_RADIUS)?.apply {
            duration = ANIMATION_DURATION
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.devRootLayout.visibility = INVISIBLE
                    supportFinishAfterTransition()
                }
            })
            start()
        }
    }

    private fun getFinalRadius() =
        (max(binding.devRootLayout.width, binding.devRootLayout.height) * RADIUS_MULTIPLIER).toFloat()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (rotationViewModel.isDualMode.value == true) {
            menuInflater.inflate(R.menu.dev_mode_menu, menu)
            menu?.findItem(R.id.menu_main_user_mode)?.actionView?.setOnClickListener {
                unRevealActivity(it)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (rotationViewModel.isDualMode.value == true) {
            supportFinishAfterTransition()
        } else {
            if (navigator.isNavigationAtStart()) {
                supportFinishAfterTransition()
            } else {
                super.onBackPressed()
            }
            overridePendingTransition(0, 0)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)

        SurfaceDuoNavigation.findNavController(this, R.id.devmode_nav_host_fragment).let {
            navigator.bind(it)
        }
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)

        navigator.unbind()
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        rotationViewModel.isDualMode.value = screenInfo.isDualMode()
        rotationViewModel.currentRotation.value = screenInfo.getScreenRotation()
    }

    companion object {
        const val EXTRA_APP_SCREEN = "EXTRA_APP_SCREEN"
        const val EXTRA_DESIGN_PATTERN = "EXTRA_DESIGN_PATTERN"
        const val EXTRA_SDK_COMPONENT = "EXTRA_SDK_COMPONENT"

        const val EXTRA_ANIMATION_X = "EXTRA_ANIMATION_X"
        const val EXTRA_ANIMATION_Y = "EXTRA_ANIMATION_Y"
        const val ANIMATION_DURATION = 750L
        const val RADIUS_MULTIPLIER = 1.1
        const val START_RADIUS = 0f

        const val SHARED_ELEMENT_NAME = "transition"
    }
}
