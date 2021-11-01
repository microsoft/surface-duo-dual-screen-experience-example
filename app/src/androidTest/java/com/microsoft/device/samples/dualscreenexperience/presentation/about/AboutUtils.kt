/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.about

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.util.forceClick
import com.microsoft.device.samples.dualscreenexperience.util.scrollNestedScrollViewTo

fun checkToolbarAbout() {
    onView(withId(R.id.toolbar)).check(matches(hasDescendant(withId(R.id.menu_main_about))))
}

fun openAbout() {
    onView(withId(R.id.menu_main_about)).perform(forceClick())
}

fun checkAboutInSingleScreenMode() {
    checkAboutSectionInSingleScreen()
    checkLinksSection()

    scrollToTerms()

    checkTermsLicensesSection()

    scrollToLicenses()

    checkLicensesSection()

    scrollToFeedbackInSingleScreenMode()

    checkFeedbackSectionInSingleScreen()
}

fun checkAboutInDualScreenMode() {
    checkAboutSectionInDualScreen()
    checkFeedbackSectionInDualScreen()

    checkLinksSection()
    checkTermsLicensesSection()

    scrollToLicenses()

    checkLicensesSection()
}

fun checkAboutSectionInSingleScreen() {
    onView(withId(R.id.about_single_screen_title)).check(matches(isDisplayed()))
    onView(withId(R.id.about_single_screen_description)).check(matches(isDisplayed()))
}

fun checkAboutSectionInDualScreen() {
    onView(withId(R.id.about_title)).check(matches(isDisplayed()))
    onView(withId(R.id.about_description)).check(matches(isDisplayed()))
}

fun checkFeedbackSectionInSingleScreen() {
    onView(withId(R.id.feedback_single_screen_title)).check(matches(isDisplayed()))
    onView(withId(R.id.feedback_single_screen_description)).check(matches(isDisplayed()))
}

fun checkFeedbackSectionInDualScreen() {
    onView(withId(R.id.feedback_title)).check(matches(isDisplayed()))
    onView(withId(R.id.feedback_description)).check(matches(isDisplayed()))
}

fun checkLinksSection() {
    onView(withId(R.id.links_title)).check(matches(isDisplayed()))
    onView(withId(R.id.links_items)).check(matches(isDisplayed()))

    onView(withId(R.id.link_docs)).check(matches(isDisplayed()))
    onView(withId(R.id.link_github)).check(matches(isDisplayed()))
    onView(withId(R.id.link_blog)).check(matches(isDisplayed()))
    onView(withId(R.id.link_twitch)).check(matches(isDisplayed()))
    onView(withId(R.id.link_figma)).check(matches(isDisplayed()))
    onView(withId(R.id.link_learn)).check(matches(isDisplayed()))
    onView(withId(R.id.link_twitter)).check(matches(isDisplayed()))
    onView(withId(R.id.link_youtube)).check(matches(isDisplayed()))
}

fun checkTermsLicensesSection() {
    onView(withId(R.id.licenses_title)).check(matches(isDisplayed()))
    onView(withId(R.id.license_terms_title)).check(matches(isDisplayed()))
}

fun checkLicensesSection() {
    onView(withId(R.id.license_terms_other_title)).check(matches(isDisplayed()))
    onView(withId(R.id.license_recycler_view)).check(matches(isDisplayed()))
}

fun scrollToTerms() {
    onView(withId(R.id.licenses_scroll_container)).perform(scrollNestedScrollViewTo(R.id.licenses_title))
}

fun scrollToLicenses() {
    onView(withId(R.id.licenses_scroll_container)).perform(scrollNestedScrollViewTo(R.id.license_recycler_view))
}

fun scrollToFeedbackInSingleScreenMode() {
    onView(withId(R.id.licenses_scroll_container)).perform(scrollNestedScrollViewTo(R.id.feedback_single_screen_title))
}
