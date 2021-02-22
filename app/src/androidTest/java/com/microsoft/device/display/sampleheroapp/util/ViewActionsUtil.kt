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
import org.hamcrest.Matcher

fun clickChildViewWithId(childId: Int) = object : ViewAction {
    override fun getConstraints(): Matcher<View>? = null

    override fun getDescription(): String = "Click action for a child view with specified id"

    override fun perform(uiController: UiController?, view: View?) {
        view?.findViewById<View>(childId)?.performClick()
    }
}
