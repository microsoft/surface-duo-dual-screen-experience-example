/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.launch

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

    fun triggerShouldShowTutorial(screenInLandscape: Boolean) {
        shouldShowTutorial.value =
            if (tutorialPrefs.shouldShowLaunchTutorial()) {
                getTutorialType(screenInLandscape).ordinal
            } else {
                SHOULD_NOT_SHOW
            }
    }

    fun dismissTutorial() {
        if (shouldShowTutorial.value != SHOULD_NOT_SHOW) {
            shouldShowTutorial.value = SHOULD_NOT_SHOW
        }
    }

    private fun getTutorialType(screenInLandscape: Boolean) =
        if (screenInLandscape) {
            TutorialBalloonType.LAUNCH_RIGHT
        } else {
            TutorialBalloonType.LAUNCH_BOTTOM
        }

    companion object {
        const val SHOULD_NOT_SHOW = -1
    }
}
