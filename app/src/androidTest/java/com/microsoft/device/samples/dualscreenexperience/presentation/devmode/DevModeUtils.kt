/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.devmode

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.util.forceClick
import com.microsoft.device.samples.dualscreenexperience.util.scrollNestedScrollViewTo
import org.hamcrest.core.AllOf.allOf

fun checkToolbarDevItem() {
    onView(withId(R.id.toolbar)).check(
        matches(
            allOf(
                hasDescendant(withId(R.id.dev_mode_action)),
                hasDescendant(withId(R.id.dev_mode_label))
            )
        )
    )
}

fun checkToolbarUserItem() {
    onView(withId(R.id.toolbar)).check(
        matches(
            allOf(
                hasDescendant(withId(R.id.user_mode_action)),
                hasDescendant(withId(R.id.user_mode_label))
            )
        )
    )
}

fun navigateUp() {
    onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(forceClick())
}

fun openDevMode() {
    onView(withId(R.id.menu_main_dev_mode)).perform(forceClick())
}

fun openUserMode() {
    onView(withId(R.id.menu_main_user_mode)).perform(forceClick())
}

fun openDevModeInDualMode(hasDesignPattern: Boolean = true) {
    checkToolbarDevItem()

    openDevMode()
    checkDevModeControl(hasDesignPattern)
    checkDevModeContent()
}

fun checkDevModeControl(hasDesignPattern: Boolean) {
    onView(withId(R.id.dev_control_title)).check(matches(isDisplayed()))
    if (hasDesignPattern) {
        onView(withId(R.id.dev_control_design_patterns)).check(matches(isDisplayed()))
    }
    onView(withId(R.id.dev_control_code)).check(matches(isDisplayed()))

    onView(withId(R.id.dev_mode_scroll)).perform(scrollNestedScrollViewTo(R.id.dev_control_sdk))
    onView(withId(R.id.dev_control_sdk)).check(matches(isDisplayed()))
}

fun checkDevModeContent() {
    onView(withId(R.id.dev_content_web_view)).check(matches(isDisplayed()))
}

fun clickDevModeDesignPatternsButton() {
    onView(withId(R.id.dev_control_design_patterns)).perform(forceClick())
}

fun clickDevModeCodeButton() {
    onView(withId(R.id.dev_control_code)).perform(forceClick())
}

fun clickDevModeSdkButton() {
    onView(withId(R.id.dev_control_sdk)).perform(forceClick())
}
