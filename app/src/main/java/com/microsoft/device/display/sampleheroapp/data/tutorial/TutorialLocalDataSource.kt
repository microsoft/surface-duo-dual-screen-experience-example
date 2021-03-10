/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.tutorial

import android.content.SharedPreferences
import com.microsoft.device.display.sampleheroapp.config.LocalStorageConfig.PrefTutorialKey
import javax.inject.Inject

class TutorialLocalDataSource @Inject constructor(
    private val sharedPref: SharedPreferences
) : TutorialDataSource {

    override suspend fun shouldShowTutorial(key: PrefTutorialKey) =
        sharedPref.getBoolean(key.toString(), true)

    override suspend fun setShowTutorial(key: PrefTutorialKey, newValue: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(key.toString(), newValue)
            apply()
        }
    }
}
