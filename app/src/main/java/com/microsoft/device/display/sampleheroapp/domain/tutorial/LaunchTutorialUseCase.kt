/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.tutorial

import com.microsoft.device.display.sampleheroapp.config.LocalStorageConfig
import com.microsoft.device.display.sampleheroapp.data.tutorial.TutorialDataSource
import javax.inject.Inject

class LaunchTutorialUseCase @Inject constructor(
    private val tutorialRepository: TutorialDataSource
) {
    private var showTutorialCacheValue = true

    suspend fun shouldShowTutorialUseCase(): Boolean {
        if (showTutorialCacheValue) {
            showTutorialCacheValue =
                tutorialRepository.shouldShowTutorial(LocalStorageConfig.PrefTutorialKey.LAUNCH)
        }
        return showTutorialCacheValue
    }

    suspend fun setShowTutorialUseCase(newValue: Boolean) {
        if (showTutorialCacheValue) {
            tutorialRepository.setShowTutorial(LocalStorageConfig.PrefTutorialKey.LAUNCH, newValue)
            showTutorialCacheValue = newValue
        }
    }
}
