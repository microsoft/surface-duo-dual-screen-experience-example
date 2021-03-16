/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util.tutorial

import androidx.lifecycle.ViewModel
import com.microsoft.device.display.sampleheroapp.common.prefs.TutorialPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val tutorialPrefs: TutorialPreferences
) : ViewModel() {

    fun onDualMode() {
        tutorialPrefs.setShowLaunchTutorial(false)
    }
}
