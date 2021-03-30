/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.util

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers

fun clickChildViewWithId(childId: Int) = object : ViewAction {
    override fun getConstraints(): Matcher<View>? = null

    override fun getDescription(): String = "Click action for a child view with specified id"

    override fun perform(uiController: UiController?, view: View?) {
        view?.findViewById<View>(childId)?.performClick()
    }
}

/**
 * Returns an action that clicks the view without to check the coordinates on the screen.
 * Seems that ViewActions.click() finds coordinates of the view on the screen, and then performs the tap on the coordinates.
 * Seems that changing the screen rotations affects these coordinates and ViewActions.click() throws exceptions.
 */
fun forceClick() = object : ViewAction {
    override fun getConstraints(): Matcher<View> =
        Matchers.allOf(ViewMatchers.isClickable(), ViewMatchers.isEnabled())

    override fun getDescription(): String = "force click"

    override fun perform(uiController: UiController?, view: View?) {
        view?.performClick()
        uiController?.loopMainThreadUntilIdle()
    }
}
