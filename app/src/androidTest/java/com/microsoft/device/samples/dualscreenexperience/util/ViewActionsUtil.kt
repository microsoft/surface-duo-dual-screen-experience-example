/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.util

import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import com.microsoft.device.dualscreen.testing.ForceClick
import com.microsoft.device.dualscreen.utils.wm.locationOnScreen
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf.allOf

fun clickChildViewWithId(childId: Int) = object : ViewAction {
    override fun getConstraints(): Matcher<View> = allOf(isClickable(), isEnabled())

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
fun forceClick() = ForceClick()

fun scrollRecyclerViewTo(viewId: Int) = object : ViewAction {
    override fun getConstraints(): Matcher<View>? =
        allOf(isAssignableFrom(RecyclerView::class.java), isDisplayed())

    override fun getDescription(): String = "Scroll RecyclerView to specific view with ID"

    override fun perform(uiController: UiController?, view: View?) {
        val recyclerView = view as RecyclerView
        val viewToReach = view.findViewById<View>(viewId)
        val position = recyclerView.getChildAdapterPosition(viewToReach)
        recyclerView.scrollToPosition(position)
        uiController?.loopMainThreadUntilIdle()
    }
}

fun scrollRecyclerViewToEnd() = object : ViewAction {
    override fun getConstraints(): Matcher<View>? =
        allOf(isAssignableFrom(RecyclerView::class.java), isDisplayed())

    override fun getDescription(): String = "Scroll RecyclerView to last position"

    override fun perform(uiController: UiController?, view: View?) {
        val recyclerView = view as RecyclerView
        val itemCount = recyclerView.adapter?.itemCount
        val position = itemCount?.minus(1) ?: 0
        recyclerView.scrollToPosition(position)
        uiController?.loopMainThreadUntilIdle()
    }
}

fun scrollNestedScrollViewTo(viewId: Int) = object : ViewAction {
    override fun getConstraints(): Matcher<View>? =
        allOf(isAssignableFrom(NestedScrollView::class.java), isDisplayed())

    override fun getDescription(): String = "Scroll NestedScrollView to specific view with ID"

    override fun perform(uiController: UiController?, view: View?) {
        val scrollView = view as NestedScrollView
        val viewToReach = view.findViewById<View>(viewId)
        if (viewToReach.parent == scrollView || viewToReach.parent.parent == scrollView) {
            scrollView.scrollTo(0, viewToReach.top)
        } else {
            scrollView.scrollTo(0, viewToReach.locationOnScreen.y)
        }
        uiController?.loopMainThreadUntilIdle()
    }
}
