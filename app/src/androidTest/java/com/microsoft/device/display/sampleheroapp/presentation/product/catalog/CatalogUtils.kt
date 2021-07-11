/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.catalog

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.util.horizontalSwipeToLeftOnLeftScreen
import com.microsoft.device.display.sampleheroapp.util.horizontalSwipeToRightOnLeftScreen
import org.hamcrest.CoreMatchers

fun checkCatalogPageIsDisplayed(pageNo: Int) {
    onView(
        CoreMatchers.allOf(
            withId(R.id.pages),
            isDisplayed()
        )
    ).check(matches(ViewMatchers.withText("Page $pageNo of 8")))
}

fun swipeViewPagerToTheLeft() {
    horizontalSwipeToLeftOnLeftScreen()
    Thread.sleep(1000)
}

fun swipeViewPagerToTheRight() {
    horizontalSwipeToRightOnLeftScreen()
    Thread.sleep(1000)
}
