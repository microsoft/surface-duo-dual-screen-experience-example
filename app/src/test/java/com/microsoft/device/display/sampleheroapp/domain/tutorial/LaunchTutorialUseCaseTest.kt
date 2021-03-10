/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.tutorial

import com.microsoft.device.display.sampleheroapp.domain.tutorial.testutil.MockTutorialDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LaunchTutorialUseCaseTest {

    private lateinit var launchTutorialUseCase: LaunchTutorialUseCase
    private lateinit var mockRepo: MockTutorialDataSource

    @Before
    fun setup() {
        mockRepo = MockTutorialDataSource()
        launchTutorialUseCase = LaunchTutorialUseCase(mockRepo)
    }

    @Test
    fun shouldShowTutorialWhenValueIsDefault() = runBlocking {
        assertTrue(launchTutorialUseCase.shouldShowTutorialUseCase())
    }

    @Test
    fun shouldNotShowTutorialWhenValueIsSetToFalse() = runBlocking {
        launchTutorialUseCase.setShowTutorialUseCase(false)
        assertFalse(launchTutorialUseCase.shouldShowTutorialUseCase())
    }

    @Test
    fun shouldNotShowTutorialWhenValueIsReset() = runBlocking {
        launchTutorialUseCase.setShowTutorialUseCase(false)
        launchTutorialUseCase.setShowTutorialUseCase(true)
        assertFalse(launchTutorialUseCase.shouldShowTutorialUseCase())
    }
}
