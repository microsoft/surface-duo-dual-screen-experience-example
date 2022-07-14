/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.common.prefs

interface TutorialPreferences {
    fun shouldShowLaunchTutorial(): Boolean
    fun setShowLaunchTutorial(value: Boolean)
    fun shouldShowDevModeTutorial(): Boolean
    fun setShowDevModeTutorial(value: Boolean)
    fun shouldShowHistoryTutorial(): Boolean
    fun setShowHistoryTutorial(value: Boolean)
}

enum class TutorialPrefType { LAUNCH, DEV_MODE, HISTORY }
