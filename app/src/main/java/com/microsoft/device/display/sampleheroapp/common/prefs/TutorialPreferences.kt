/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.common.prefs

interface TutorialPreferences {
    fun shouldShowLaunchTutorial(): Boolean
    fun setShowLaunchTutorial(value: Boolean)
    fun shouldShowViewCodeTutorial(): Boolean
    fun setShowViewCodeTutorial(value: Boolean)
    fun shouldShowStoresTutorial(): Boolean
    fun setShowStoresTutorial(value: Boolean)
}

enum class TutorialPrefType { LAUNCH, VIEW_CODE, STORES }
