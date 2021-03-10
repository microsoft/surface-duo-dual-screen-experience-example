/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.tutorial

import android.content.Context
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.device.display.sampleheroapp.config.LocalStorageConfig
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TutorialRepositoryTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val sharedPref = context.getSharedPreferences(
        LocalStorageConfig.PREF_NAME_TEST,
        Context.MODE_PRIVATE
    )
    private lateinit var tutorialRepo: TutorialRepository

    @Before
    fun createRepo() {
        sharedPref.edit().clear().commit()
        tutorialRepo = TutorialRepository(TutorialLocalDataSource(sharedPref))
    }

    @Test
    fun shouldShowTutorialWhenValueIsDefault() = runBlocking {
        assertTrue(tutorialRepo.shouldShowTutorial(LocalStorageConfig.PrefTutorialKey.LAUNCH))
    }

    @Test
    fun shouldNotShowTutorialWhenValueIsSetToFalse() = runBlocking {
        tutorialRepo.setShowTutorial(LocalStorageConfig.PrefTutorialKey.LAUNCH, false)
        assertFalse(tutorialRepo.shouldShowTutorial(LocalStorageConfig.PrefTutorialKey.LAUNCH))
    }

    @Test
    fun shouldShowTutorialWhenValueIsSetToTrue() = runBlocking {
        assertTrue(tutorialRepo.shouldShowTutorial(LocalStorageConfig.PrefTutorialKey.LAUNCH))
        tutorialRepo.setShowTutorial(LocalStorageConfig.PrefTutorialKey.LAUNCH, false)
        tutorialRepo.setShowTutorial(LocalStorageConfig.PrefTutorialKey.LAUNCH, true)
        assertTrue(tutorialRepo.shouldShowTutorial(LocalStorageConfig.PrefTutorialKey.LAUNCH))
    }
}
