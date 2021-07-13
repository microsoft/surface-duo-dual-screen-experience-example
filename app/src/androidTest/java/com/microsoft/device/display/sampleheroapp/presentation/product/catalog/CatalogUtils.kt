/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.catalog

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.microsoft.device.display.sampleheroapp.R
import org.hamcrest.core.AllOf.allOf

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

fun swipeHostViewPagerToTheRight() {
    onView(withId(R.id.catalog_view_pager)).perform(swipeRight())
}

fun openCatalogTab() {
    onView(
        allOf(
            withText(R.string.main_products_tab_catalog),
            isDescendantOfA(withId(R.id.catalog_tab_layout))
        )
    ).perform(click())
}

fun openProductsTab() {
    onView(
        allOf(
            withText(R.string.main_products_tab_products),
            isDescendantOfA(withId(R.id.catalog_tab_layout))
        )
    ).perform(click())
}
