/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.launch

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.SurfaceDuoNavigation
import com.microsoft.device.dualscreen.ScreenInfo
import com.microsoft.device.dualscreen.ScreenInfoListener
import com.microsoft.device.dualscreen.ScreenManagerProvider
import com.microsoft.device.dualscreen.isSurfaceDuoDevice
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.presentation.launch.LaunchViewModel.Companion.SHOULD_NOT_SHOW
import com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial.TutorialBalloon
import com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial.TutorialBalloonType
import com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial.TutorialViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchActivity : AppCompatActivity(), ScreenInfoListener {

    private val viewModel: LaunchViewModel by viewModels()
    private val tutorialViewModel: TutorialViewModel by viewModels()

    @Inject lateinit var navigator: LaunchNavigator
    @Inject lateinit var tutorial: TutorialBalloon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.shouldShowTutorial.observe(this, { handleTutorial(it) })
    }

    override fun onResume() {
        super.onResume()
        ScreenManagerProvider.getScreenManager().addScreenInfoListener(this)

        SurfaceDuoNavigation.findNavController(this, R.id.launch_nav_host_fragment).let {
            navigator.bind(it)
        }
    }

    override fun onPause() {
        super.onPause()
        ScreenManagerProvider.getScreenManager().removeScreenInfoListener(this)
        dismissTutorialBalloon()
        navigator.unbind()
    }

    override fun onScreenInfoChanged(screenInfo: ScreenInfo) {
        viewModel.isDualMode.value = screenInfo.isDualMode()
        if (screenInfo.isDualMode()) {
            tutorialViewModel.onDualMode()
            dismissTutorial()
        } else if (isSurfaceDuoDevice()) {
            viewModel.triggerShouldShowTutorial(screenInfo.getScreenRotation())
        }
    }

    private fun dismissTutorialBalloon() {
        tutorial.hide()
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
        tutorial.show(window.decorView, TutorialBalloonType.values()[ordinal])
    }

    private fun dismissTutorial() {
        dismissTutorialBalloon()
        viewModel.dismissTutorial()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        finish()
    }
}
