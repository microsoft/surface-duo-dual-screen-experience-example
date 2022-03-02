/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.dualscreen.testing.spanFromStart
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.navigateUp

open class BaseStoreNavigationTest {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun openMapInSingleMode() {
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openDetailsFromMapInSingleMode() {
        clickOnMapMarker(storeWithoutCity.name)
        checkDetailsFragment(storeWithoutCity)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openDetailsFromMapInDualMode() {
        device.spanFromStart()

        clickOnMapMarker(storeWithoutCity.name)
        checkMapFragment()
        checkDetailsFragment(storeWithoutCity)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openListFromMapInSingleMode() {
        clickOnMapMarker(cityRedmond.name)
        checkListFragment(cityRedmond.name, STORE_FIRST_POSITION, firstStore)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openListFromMapInDualMode() {
        device.spanFromStart()

        clickOnMapMarker(cityRedmond.name)
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_FIRST_POSITION, firstStore)
        checkListFragmentInEmptyState(device)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openListFromDetailsInDualMode() {
        device.spanFromStart()

        clickOnMapMarker(storeWithoutCity.name)
        checkMapFragment()
        checkDetailsFragment(storeWithoutCity)

        clickOnMapMarker(cityRedmond.name)
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_FIRST_POSITION, firstStore)
        checkListFragmentInEmptyState(device)

        navigateUp()
        checkMapFragment()
        checkSelectedBeforeListStoreDetailsFragment(storeWithoutCity)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openDetailsFromListInSingleMode() {
        clickOnMapMarker(cityRedmond.name)
        clickOnListItemAtPosition(STORE_FIRST_POSITION)
        checkDetailsFragment(firstStore)

        navigateUp()
        checkListFragment(cityRedmond.name, STORE_FIRST_POSITION, firstStore)
        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openDetailsFromListInDualMode() {
        device.spanFromStart()

        clickOnMapMarker(cityRedmond.name)
        clickOnListItemAtPosition(STORE_FIRST_POSITION)
        checkMapFragment()
        checkDetailsFragment(firstStore)

        navigateUp()
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_FIRST_POSITION, firstStore)
        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }
}
