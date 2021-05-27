/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.common.prefs

import android.content.Context
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.device.display.sampleheroapp.config.SharedPrefConfig.PREF_NAME_TEST
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class PreferenceManagerTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val sharedPref = context.getSharedPreferences(PREF_NAME_TEST, Context.MODE_PRIVATE)
    private lateinit var tutorialPrefManager: PreferenceManager

    @Before
    fun resetPrefs() {
        sharedPref.edit().clear().commit()
        tutorialPrefManager = PreferenceManager(sharedPref)
    }

    @Test
    fun shouldShowTutorialWhenValueIsDefault() {
        assertTrue(tutorialPrefManager.shouldShowLaunchTutorial())
        assertTrue(tutorialPrefManager.shouldShowDevModeTutorial())
        assertTrue(tutorialPrefManager.shouldShowStoresTutorial())
    }

    @Test
    fun shouldNotShowTutorialWhenValueIsSetToFalse() {
        tutorialPrefManager.setShowLaunchTutorial(false)
        tutorialPrefManager.setShowDevModeTutorial(false)
        tutorialPrefManager.setShowStoresTutorial(false)

        assertFalse(tutorialPrefManager.shouldShowLaunchTutorial())
        assertFalse(tutorialPrefManager.shouldShowDevModeTutorial())
        assertFalse(tutorialPrefManager.shouldShowStoresTutorial())
    }

    @Test
    fun shouldShowTutorialWhenValueIsSetToTrue() {
        assertTrue(tutorialPrefManager.shouldShowLaunchTutorial())
        tutorialPrefManager.setShowLaunchTutorial(false)
        tutorialPrefManager.setShowLaunchTutorial(true)
        assertFalse(tutorialPrefManager.shouldShowLaunchTutorial())

        assertTrue(tutorialPrefManager.shouldShowDevModeTutorial())
        tutorialPrefManager.setShowDevModeTutorial(false)
        tutorialPrefManager.setShowDevModeTutorial(true)
        assertFalse(tutorialPrefManager.shouldShowDevModeTutorial())

        assertTrue(tutorialPrefManager.shouldShowStoresTutorial())
        tutorialPrefManager.setShowStoresTutorial(false)
        tutorialPrefManager.setShowStoresTutorial(true)
        assertFalse(tutorialPrefManager.shouldShowStoresTutorial())
    }
}
