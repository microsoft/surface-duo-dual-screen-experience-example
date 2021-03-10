/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.tutorial.testutil

import com.microsoft.device.display.sampleheroapp.config.LocalStorageConfig.PrefTutorialKey
import com.microsoft.device.display.sampleheroapp.data.tutorial.TutorialDataSource

class MockTutorialDataSource : TutorialDataSource {
    private var showTutorialValue = true

    override suspend fun shouldShowTutorial(key: PrefTutorialKey) = showTutorialValue

    override suspend fun setShowTutorial(key: PrefTutorialKey, newValue: Boolean) {
        showTutorialValue = newValue
    }
}
