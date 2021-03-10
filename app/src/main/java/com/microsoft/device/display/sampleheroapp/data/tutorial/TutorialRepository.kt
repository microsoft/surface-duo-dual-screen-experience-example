/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.tutorial

import com.microsoft.device.display.sampleheroapp.config.LocalStorageConfig.PrefTutorialKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TutorialRepository @Inject constructor(
    private val localDataSource: TutorialLocalDataSource
) : TutorialDataSource {

    override suspend fun shouldShowTutorial(key: PrefTutorialKey) =
        localDataSource.shouldShowTutorial(key)

    override suspend fun setShowTutorial(key: PrefTutorialKey, newValue: Boolean) {
        localDataSource.setShowTutorial(key, newValue)
    }
}
