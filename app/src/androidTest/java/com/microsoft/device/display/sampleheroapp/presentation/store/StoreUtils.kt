/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store

import android.view.Surface
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By.descContains
import androidx.test.uiautomator.By.textContains
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.data.store.cityEntityRedmond
import com.microsoft.device.display.sampleheroapp.data.store.storeEntityJoy
import com.microsoft.device.display.sampleheroapp.domain.store.model.City
import com.microsoft.device.display.sampleheroapp.domain.store.model.Store
import com.microsoft.device.display.sampleheroapp.domain.store.model.StoreImage
import com.microsoft.device.display.sampleheroapp.util.atRecyclerAdapterPosition
import com.microsoft.device.display.sampleheroapp.util.clickChildViewWithId
import com.microsoft.device.display.sampleheroapp.util.withToolbarTitle
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.core.AllOf.allOf

fun checkMapFragment() {
    onView(withId(R.id.map_container)).check(matches(isDisplayed()))
    onView(withId(R.id.reset_fab)).check(matches(isDisplayed()))
}

fun checkListFragment(cityName: String, position: Int, store: Store) {
    onView(withId(R.id.store_list_title)).check(matches(allOf(isDisplayed(), withText(cityName))))
    onView(withId(R.id.store_list)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.item_store_name,
                withText(containsString(store.name)),
            )
        )
    )
    onView(withId(R.id.store_list)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.item_store_address,
                withText(store.address),
            )
        )
    )
    onView(withId(R.id.store_list)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.item_store_rating,
                withText(store.rating.toString()),
            )
        )
    )
    onView(withId(R.id.store_list)).check(
        matches(
            atRecyclerAdapterPosition(
                position,
                R.id.item_store_reviews,
                withText(
                    containsString(
                        store.reviewCount.toString()
                    )
                )
            )
        )
    )

    checkToolbar(R.string.toolbar_stores_title)
}

fun checkListFragmentInEmptyState() {
    moveMap()
    onView(withId(R.id.store_list_empty_image)).check(matches(isDisplayed()))
    onView(withId(R.id.store_list_empty_message)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(R.string.store_list_empty_message)
            )
        )
    )
    resetMap()
}

fun moveMap() {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    when (device.displayRotation) {
        Surface.ROTATION_0 -> device.swipe(637, 1378, 1204, 73, 400)
        Surface.ROTATION_270 -> device.swipe(816, 1075, 1480, 49, 400)
    }
}

fun resetMap() {
    onView(withId(R.id.reset_fab)).check(matches(isDisplayed()))
    onView(withId(R.id.reset_fab)).perform(click())
}

fun checkDetailsFragment(store: Store) {
    onView(withId(R.id.store_details_name)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(store.name)
            )
        )
    )
    onView(withId(R.id.store_details_review_ratings)).check(
        matches(
            allOf(
                isDisplayed(),
                hasDescendant(withText(store.rating.toString())),
                hasDescendant(withText(containsString(store.reviewCount.toString())))
            )
        )
    )
    checkDetailsAbout(store)
    moveToContactTab()
    checkDetailsContact(store)

    checkToolbar(R.string.store_title, store.name)
}

fun checkDetailsAbout(store: Store) {
    onView(withId(R.id.store_details_about_description)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(
                    containsString(
                        store.name
                    )
                )
            )
        )
    )
}

fun moveToContactTab() {
    onView(withId(R.id.store_details_pager)).perform(swipeLeft())
}

fun checkDetailsContact(store: Store) {
    onView(withId(R.id.store_details_contact_address_text)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(store.address)
            )
        )
    )
    onView(withId(R.id.store_details_contact_city_text)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(store.cityStateCode)
            )
        )
    )
    onView(withId(R.id.store_details_contact_info_phone)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(store.phoneNumber)
            )
        )
    )
    onView(withId(R.id.store_details_contact_info_email)).check(
        matches(
            allOf(
                isDisplayed(),
                withText(store.emailAddress)
            )
        )
    )
}

fun checkToolbar(@StringRes titleRes: Int, titleParam: String? = null) {
    onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
    onView(withId(R.id.toolbar)).check(matches(withToolbarTitle(titleRes, titleParam)))
}

fun clickOnMapMarker(markerTitle: String) {
    val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    val bySelectorMarker = descContains(markerTitle)
    val bySelectorMarkerClicked = textContains(markerTitle)
    device.wait(Until.hasObject(bySelectorMarker), MAX_TIMEOUT)
    device.findObject(bySelectorMarker).click()
    device.wait(Until.hasObject(bySelectorMarkerClicked), MAX_TIMEOUT)
}

fun navigateUp() {
    onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
}

fun clickOnListItemAtPosition(position: Int) {
    onView(withId(R.id.store_list)).perform(
        actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position,
            clickChildViewWithId(R.id.item_store_view_button)
        )
    )
}

const val MAX_TIMEOUT = 5000L
const val STORE_JOY_POSITION = 0

val storeJoy = Store(storeEntityJoy)

val storeMehul = Store(
    101,
    "Mehul's",
    "1201 NE 52nd St",
    null,
    "Seattle, WA 98105",
    "(206)-473-3864",
    "musicals@mehul.com",
    47.6103596,
    -122.3283307,
    4.8f,
    59,
    StoreImage.MEHUL
)

val cityRedmond = City(cityEntityRedmond)
