/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.util

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withToolbarTitle(@StringRes titleRes: Int, titleParam: String? = null): Matcher<View?> =
    object : BoundedMatcher<View?, Toolbar>(Toolbar::class.java) {

        override fun describeTo(description: Description) {
            description.appendText("Has the title matching with the string res value ")
        }

        override fun matchesSafely(toolbar: Toolbar): Boolean {
            return if (titleParam == null) {
                toolbar.title == toolbar.context.getString(titleRes)
            } else {
                toolbar.title == toolbar.context.getString(titleRes, titleParam)
            }
        }
    }

fun atRecyclerAdapterPosition(
    position: Int,
    @IdRes childId: Int,
    itemMatcher: Matcher<View?>
): Matcher<View?> =
    object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("has item at position $position: ")
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position) ?: return false
            return itemMatcher.matches(viewHolder.itemView.findViewById(childId))
        }
    }
