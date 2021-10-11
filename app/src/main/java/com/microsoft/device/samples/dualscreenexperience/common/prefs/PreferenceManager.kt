/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.common.prefs

import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    private val sharedPref: SharedPreferences
) : TutorialPreferences {

    override fun shouldShowLaunchTutorial() =
        sharedPref.getBoolean(TutorialPrefType.LAUNCH.toString(), true)

    override fun setShowLaunchTutorial(value: Boolean) {
        if (shouldShowLaunchTutorial()) {
            sharedPref.setValue(TutorialPrefType.LAUNCH.toString(), value)
        }
    }

    override fun shouldShowDevModeTutorial() =
        sharedPref.getBoolean(TutorialPrefType.DEV_MODE.toString(), true)

    override fun setShowDevModeTutorial(value: Boolean) {
        if (shouldShowDevModeTutorial()) {
            sharedPref.setValue(TutorialPrefType.DEV_MODE.toString(), value)
        }
    }

    override fun shouldShowStoresTutorial() =
        sharedPref.getBoolean(TutorialPrefType.STORES.toString(), true)

    override fun setShowStoresTutorial(value: Boolean) {
        if (shouldShowStoresTutorial()) {
            sharedPref.setValue(TutorialPrefType.STORES.toString(), value)
        }
    }
}
