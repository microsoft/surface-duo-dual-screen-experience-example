/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.util.tutorial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.microsoft.device.samples.dualscreenexperience.common.prefs.TutorialPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val tutorialPrefs: TutorialPreferences
) : ViewModel() {
    var showHistoryTutorial = MutableLiveData<Boolean?>(null)

    fun updateTutorial() {
        if (tutorialPrefs.shouldShowHistoryTutorial()) {
            showHistoryTutorial.value = true
        }
    }

    fun onDualMode() {
        tutorialPrefs.setShowLaunchTutorial(false)
    }

    fun onHistoryOpen() {
        if (showHistoryTutorial.value != null) {
            tutorialPrefs.setShowHistoryTutorial(false)
        }
    }

    fun onDeveloperModeOpen() {
        tutorialPrefs.setShowDevModeTutorial(false)
    }

    fun shouldShowDeveloperModeTutorial() = tutorialPrefs.shouldShowDevModeTutorial()

    fun shouldShowHistoryTutorial() = tutorialPrefs.shouldShowHistoryTutorial()
}
