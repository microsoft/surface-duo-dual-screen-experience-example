/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util.tutorial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.microsoft.device.display.sampleheroapp.common.prefs.TutorialPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val tutorialPrefs: TutorialPreferences
) : ViewModel() {
    var showStoresTutorial = MutableLiveData<Boolean?>(null)

    fun updateTutorial() {
        if (tutorialPrefs.shouldShowStoresTutorial()) {
            showStoresTutorial.value = true
        }
    }

    fun onDualMode() {
        tutorialPrefs.setShowLaunchTutorial(false)
    }

    fun onStoresOpen() {
        if (showStoresTutorial.value != null) {
            tutorialPrefs.setShowStoresTutorial(false)
        }
    }

    fun shouldShowDeveloperModeTutorial() = tutorialPrefs.shouldShowDevModeTutorial()

    fun onDeveloperModeOpen() {
        tutorialPrefs.setShowDevModeTutorial(false)
    }
}
