/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store

import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.presentation.devmode.navigateUp
import com.microsoft.device.samples.dualscreenexperience.util.switchFromSingleToDualScreen

open class BaseStoreNavigationTest {

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
        switchFromSingleToDualScreen()

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
        switchFromSingleToDualScreen()

        clickOnMapMarker(cityRedmond.name)
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_FIRST_POSITION, firstStore)
        checkListFragmentInEmptyState()

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openListFromDetailsInDualMode() {
        switchFromSingleToDualScreen()

        clickOnMapMarker(storeWithoutCity.name)
        checkMapFragment()
        checkDetailsFragment(storeWithoutCity)

        clickOnMapMarker(cityRedmond.name)
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_FIRST_POSITION, firstStore)
        checkListFragmentInEmptyState()

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
        switchFromSingleToDualScreen()

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
