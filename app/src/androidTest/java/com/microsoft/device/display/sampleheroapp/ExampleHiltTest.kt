/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.microsoft.device.display.sampleheroapp.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ExampleHiltTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun happyFirstTest() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.map_container)).check(matches(isDisplayed()))
    }
}
