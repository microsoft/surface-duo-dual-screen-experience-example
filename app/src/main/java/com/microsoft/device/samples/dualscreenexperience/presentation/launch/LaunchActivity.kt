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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.FoldableNavigation
import androidx.window.layout.WindowInfoRepository
import androidx.window.layout.WindowInfoRepository.Companion.windowInfoRepository
import androidx.window.layout.WindowLayoutInfo
import com.microsoft.device.dualscreen.utils.wm.isInDualMode
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.presentation.launch.LaunchViewModel.Companion.SHOULD_NOT_SHOW
import com.microsoft.device.samples.dualscreenexperience.presentation.util.LayoutInfoViewModel
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isInLandscape
import com.microsoft.device.samples.dualscreenexperience.presentation.util.isSurfaceDuoDevice
import com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial.TutorialBalloon
import com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial.TutorialBalloonType
import com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial.TutorialViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LaunchActivity : AppCompatActivity() {

    private val viewModel: LaunchViewModel by viewModels()
    private val layoutInfoViewModel: LayoutInfoViewModel by viewModels()
    private val tutorialViewModel: TutorialViewModel by viewModels()

    @Inject
    lateinit var navigator: LaunchNavigator
    @Inject
    lateinit var tutorial: TutorialBalloon

    private lateinit var windowInfoRepository: WindowInfoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        setupObservers()
    }

    private fun setupObservers() {
        observeWindowLayoutInfo()
        viewModel.shouldShowTutorial.observe(this, { handleTutorial(it) })
    }

    private fun observeWindowLayoutInfo() {
        windowInfoRepository = windowInfoRepository()
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                windowInfoRepository.windowLayoutInfo.collect {
                    onWindowLayoutInfoChanged(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        FoldableNavigation.findNavController(this, R.id.launch_nav_host_fragment).let {
            navigator.bind(it)
        }
        if (layoutInfoViewModel.isDualMode.value != true && isSurfaceDuoDevice()) {
            viewModel.triggerShouldShowTutorial(isInLandscape())
        }
    }

    override fun onPause() {
        super.onPause()
        dismissTutorialBalloon()
        navigator.unbind()
    }

    private fun onWindowLayoutInfoChanged(windowLayoutInfo: WindowLayoutInfo) {
        layoutInfoViewModel.isDualMode.value = windowLayoutInfo.isInDualMode()
        if (windowLayoutInfo.isInDualMode()) {
            tutorialViewModel.onDualMode()
            dismissTutorial()
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
        if (window != null && !isFinishing) {
            tutorial.show(window.decorView, TutorialBalloonType.values()[ordinal])
        }
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
