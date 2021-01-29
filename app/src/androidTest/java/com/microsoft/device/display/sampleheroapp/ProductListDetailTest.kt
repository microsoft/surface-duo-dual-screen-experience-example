/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.microsoft.device.display.sampleheroapp.presentation.MainActivity
import com.microsoft.device.display.sampleheroapp.presentation.product.ProductAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class ProductListDetailTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun happyFirstTest() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.product_list)).check(matches(isDisplayed()))

        onView(withId(R.id.product_list)).perform(
            actionOnItemAtPosition<ProductAdapter.DummyViewHolder>(0, click())
        )

        onView(withId(R.id.detail_product_name)).check(matches(isDisplayed()))
        onView(withId(R.id.detail_product_price)).check(matches(isDisplayed()))
    }
}
