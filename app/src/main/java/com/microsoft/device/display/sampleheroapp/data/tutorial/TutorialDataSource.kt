/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.tutorial

import com.microsoft.device.display.sampleheroapp.config.LocalStorageConfig.PrefTutorialKey

interface TutorialDataSource {
    suspend fun shouldShowTutorial(key: PrefTutorialKey): Boolean
    suspend fun setShowTutorial(key: PrefTutorialKey, newValue: Boolean)
}
