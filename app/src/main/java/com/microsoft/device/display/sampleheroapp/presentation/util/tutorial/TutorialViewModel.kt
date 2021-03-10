/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.util.tutorial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.display.sampleheroapp.domain.tutorial.LaunchTutorialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorialViewModel @Inject constructor(
    private val launchTutorialUseCase: LaunchTutorialUseCase
) : ViewModel() {

    fun onDualMode() {
        viewModelScope.launch {
            launchTutorialUseCase.setShowTutorialUseCase(false)
        }
    }
}
