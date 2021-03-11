/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.launch

import android.view.Surface
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.display.sampleheroapp.domain.tutorial.LaunchTutorialUseCase
import com.microsoft.device.display.sampleheroapp.presentation.util.ItemClickListener
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TutorialType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(
    private val launchTutorialUseCase: LaunchTutorialUseCase
) : ViewModel(), ItemClickListener<Boolean> {
    val isDualMode = MutableLiveData(false)
    val isLaunchButtonClicked = MutableLiveData(false)
    val shouldShowTutorial = MutableLiveData<Int?>(null)

    override fun onClick(model: Boolean?) {
        isLaunchButtonClicked.value = true
    }

    fun triggerShouldShowTutorial(rotation: Int) {
        viewModelScope.launch {
            shouldShowTutorial.value =
                if (launchTutorialUseCase.shouldShowTutorialUseCase()) {
                    getTutorialType(rotation)?.ordinal
                } else {
                    SHOULD_NOT_SHOW
                }
        }
    }

    fun dismissTutorial() {
        if (shouldShowTutorial.value != SHOULD_NOT_SHOW) {
            shouldShowTutorial.value = SHOULD_NOT_SHOW
        }
    }

    private fun getTutorialType(surfaceRotation: Int) =
        when (surfaceRotation) {
            Surface.ROTATION_0, Surface.ROTATION_180 -> TutorialType.LAUNCH_BOTTOM
            Surface.ROTATION_90, Surface.ROTATION_270 -> TutorialType.LAUNCH_RIGHT
            else -> null
        }

    companion object {
        const val SHOULD_NOT_SHOW = -1
    }
}
