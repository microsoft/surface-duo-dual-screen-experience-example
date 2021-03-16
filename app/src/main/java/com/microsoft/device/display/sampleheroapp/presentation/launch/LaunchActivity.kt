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
import com.microsoft.device.display.sampleheroapp.presentation.launch.LaunchViewModel.Companion.SHOULD_NOT_SHOW
import com.microsoft.device.display.sampleheroapp.presentation.launch.fragments.LaunchDescriptionFragment
import com.microsoft.device.display.sampleheroapp.presentation.launch.fragments.LaunchTitleFragment
import com.microsoft.device.display.sampleheroapp.presentation.launch.fragments.SingleScreenLaunchFragment
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialBalloon
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialBalloonType
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialViewModel
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchActivity : FragmentActivity(), ScreenInfoListener {

    private val viewModel: LaunchViewModel by viewModels()
    private val tutorialViewModel: TutorialViewModel by viewModels()
    private var tutorial: TutorialBalloon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.isLaunchButtonClicked.observe(this, { navigateToMainActivity() })
        viewModel.shouldShowTutorial.observe(this, { handleTutorial(it) })
    }

    override fun onResume() {
        super.onResume()
        initTutorial()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
        destroyTutorial()
    }

    private fun navigateToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        viewModel.isDualMode.value = screenInfo.isDualMode()
        if (screenInfo.isDualMode()) {
            setupDualScreenFragments()
            tutorialViewModel.onDualMode()
            dismissTutorial()
        } else {
            viewModel.triggerShouldShowTutorial(screenInfo.getScreenRotation())
            setupSingleScreenFragments()
        }
    }

    private fun initTutorial() {
        tutorial = TutorialBalloon(applicationContext)
    }

    private fun destroyTutorial() {
        tutorial?.hide()
        tutorial = null
    }

    private fun handleTutorial(typeOrdinal: Int?) {
        typeOrdinal?.let {
            if (it == SHOULD_NOT_SHOW) {
                dismissTutorial()
            } else {
                showTutorial(it)
            }
        }
    }

    private fun showTutorial(ordinal: Int) {
        tutorial?.show(window.decorView, TutorialBalloonType.values()[ordinal])
    }

    private fun dismissTutorial() {
        tutorial?.hide()
        viewModel.dismissTutorial()
    }

    private fun setupSingleScreenFragments() {
        if (supportFragmentManager.findFragmentByTag(LAUNCH_FRAGMENT_SINGLE_SCREEN) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.first_container_id, SingleScreenLaunchFragment(), LAUNCH_FRAGMENT_SINGLE_SCREEN)
                .commit()
        }
    }

    private fun setupDualScreenFragments() {
        if (supportFragmentManager.findFragmentByTag(LAUNCH_FRAGMENT_TITLE) == null &&
            supportFragmentManager.findFragmentByTag(LAUNCH_FRAGMENT_DESCRIPTION) == null
        ) {

            supportFragmentManager.beginTransaction()
                .replace(R.id.first_container_id, LaunchTitleFragment(), LAUNCH_FRAGMENT_TITLE)
                .commit()

            supportFragmentManager.beginTransaction()
                .replace(R.id.second_container_id, LaunchDescriptionFragment(), LAUNCH_FRAGMENT_DESCRIPTION)
                .commit()
        }
    }

    companion object {
        private const val LAUNCH_FRAGMENT_TITLE = "LaunchFragmentTitle"
        private const val LAUNCH_FRAGMENT_DESCRIPTION = "LaunchFragmentDescription"
        private const val LAUNCH_FRAGMENT_SINGLE_SCREEN = "LaunchFragmentSingleScreen"
    }
}
