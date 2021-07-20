/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.catalog

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.util.forceClick
import org.hamcrest.core.AllOf.allOf

fun navigateToCatalogSection() {
    onView(withId(R.id.navigation_catalog_graph)).perform(forceClick())
}

fun checkCatalogPageIsDisplayed(pageNo: Int) {
    onView(
        allOf(
            withId(R.id.pages),
            withText("Page $pageNo of 8")
        )
    ).check(matches(isDisplayed()))
}

fun swipeCatalogViewPagerToTheLeft() {
    onView(withId(R.id.pager)).perform(swipeLeft())
}
