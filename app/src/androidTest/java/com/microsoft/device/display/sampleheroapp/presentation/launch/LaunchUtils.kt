/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.launch

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.presentation.util.tutorial.TUTORIAL_TEST_ID
import com.microsoft.device.display.sampleheroapp.util.forceClick
import org.hamcrest.core.AllOf.allOf
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue

fun checkLaunchInSingleMode() {
    checkTitleFragmentInSingleMode()
    checkSingleLaunchButton()
    checkCircleIndicators()
    moveToDescriptionTab()
    checkDescriptionFragment()
    checkSingleLaunchButton()
    checkLaunchTutorialShowing()
}

fun checkLaunchInDualMode() {
    checkTutorialNotShowing()
    checkTitleFragmentInDualMode()
    checkDescriptionFragment()
    checkDualLaunchButton()
}

fun checkTitleFragmentInSingleMode() {
    onView(withId(R.id.launch_view_pager)).check(
        matches(
            allOf(
                isDisplayed(),
                hasDescendant(allOf(withText(R.string.app_short_name), isDisplayed())),
                hasDescendant(allOf(withId(R.id.launch_image), isDisplayed()))
            )
        )
    )
}

fun checkTitleFragmentInDualMode() {
    onView(withId(R.id.launch_title)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(R.string.app_short_name)
            )
        )
    )
    onView(withId(R.id.launch_image)).check(matches(isDisplayed()))
}

fun checkDescriptionFragment() {
    onView(withId(R.id.launch_description_text_view)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(R.string.launch_description)
            )
        )
    )
    onView(withId(R.id.launch_description_image_view)).check(matches(isDisplayed()))
}

fun checkCircleIndicators() {
    onView(withId(R.id.launch_tab_layout)).check(matches(isDisplayed()))
}

fun checkSingleLaunchButton() {
    onView(withId(R.id.single_launch_button)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(R.string.launch_button)
            )
        )
    )
}

fun checkDualLaunchButton() {
    onView(withId(R.id.dual_launch_button)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(R.string.launch_button)
            )
        )
    )
}

fun checkLaunchTutorialShowing() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    assertTrue(device.hasObject(By.descContains(TUTORIAL_TEST_ID)))

    onView(withId(R.id.tutorial_balloon_text)).inRoot(isPlatformPopup()).check(
        matches(
            allOf(
                isDisplayed(),
                withText(R.string.tutorial_launch_text)
            )
        )
    )
}

fun checkTutorialNotShowing() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    assertFalse(device.hasObject(By.descContains(TUTORIAL_TEST_ID)))
}

fun clickSingleLaunchButton() {
    onView(withId(R.id.single_launch_button)).perform(forceClick())
}

fun clickDualLaunchButton() {
    onView(withId(R.id.dual_launch_button)).perform(click())
}

fun moveToDescriptionTab() {
    onView(withId(R.id.launch_view_pager)).perform(ViewActions.swipeLeft())
}

fun goBack() {
    Espresso.pressBack()
}
