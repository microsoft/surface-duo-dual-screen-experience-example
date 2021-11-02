/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.launch

import android.view.Surface
import androidx.lifecycle.ViewModel
import com.microsoft.device.samples.dualscreenexperience.common.prefs.TutorialPreferences
import com.microsoft.device.samples.dualscreenexperience.presentation.util.ItemClickListener
import com.microsoft.device.samples.dualscreenexperience.presentation.util.SingleLiveEvent
import com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial.TutorialBalloonType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val tutorialPrefs: TutorialPreferences,
    private val navigator: LaunchNavigator
) : ViewModel(), ItemClickListener<Boolean> {
    val shouldShowTutorial = SingleLiveEvent<Int?>(null)

    override fun onClick(model: Boolean?) {
        if (model == true) {
            navigator.navigateToMainFromDescription()
        } else {
            navigator.navigateToMainFromSingle()
        }
    }

    fun navigateToDescription() {
        navigator.navigateToDescription()
    }

    fun isNavigationAtDescription() = navigator.isNavigationAtDescription()

    fun navigateUp() {
        navigator.navigateUp()
    }

    fun triggerShouldShowTutorial(rotation: Int) {
        shouldShowTutorial.value =
            if (tutorialPrefs.shouldShowLaunchTutorial()) {
                getTutorialType(rotation)?.ordinal
            } else {
                SHOULD_NOT_SHOW
            }
    }

    fun dismissTutorial() {
        if (shouldShowTutorial.value != SHOULD_NOT_SHOW) {
            shouldShowTutorial.value = SHOULD_NOT_SHOW
        }
    }

    private fun getTutorialType(surfaceRotation: Int) =
        when (surfaceRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 -> TutorialBalloonType.LAUNCH_BOTTOM
            Surface.ROTATION_90, Surface.ROTATION_270 -> TutorialBalloonType.LAUNCH_RIGHT
            else -> null
        }

    companion object {
        const val SHOULD_NOT_SHOW = -1
    }
}
