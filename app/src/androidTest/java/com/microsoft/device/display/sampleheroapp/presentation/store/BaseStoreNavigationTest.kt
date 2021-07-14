/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store

import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.presentation.devmode.navigateUp
import com.microsoft.device.display.sampleheroapp.util.switchFromSingleToDualScreen

open class BaseStoreNavigationTest {

    fun openMapInSingleMode() {
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openDetailsFromMapInSingleMode() {
        clickOnMapMarker(storeMehul.name)
        checkDetailsFragment(storeMehul)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openDetailsFromMapInDualMode() {
        switchFromSingleToDualScreen()

        clickOnMapMarker(storeMehul.name)
        checkMapFragment()
        checkDetailsFragment(storeMehul)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openListFromMapInSingleMode() {
        clickOnMapMarker(cityRedmond.name)
        checkListFragment(cityRedmond.name, STORE_JOY_POSITION, storeJoy)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openListFromMapInDualMode() {
        switchFromSingleToDualScreen()

        clickOnMapMarker(cityRedmond.name)
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_JOY_POSITION, storeJoy)
        checkListFragmentInEmptyState()

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openListFromDetailsInDualMode() {
        switchFromSingleToDualScreen()

        clickOnMapMarker(storeMehul.name)
        checkMapFragment()
        checkDetailsFragment(storeMehul)

        clickOnMapMarker(cityRedmond.name)
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_JOY_POSITION, storeJoy)
        checkListFragmentInEmptyState()

        navigateUp()
        checkMapFragment()
        checkSelectedBeforeListStoreDetailsFragment(storeMehul)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openDetailsFromListInSingleMode() {
        clickOnMapMarker(cityRedmond.name)
        clickOnListItemAtPosition(STORE_JOY_POSITION)
        checkDetailsFragment(storeJoy)

        navigateUp()
        checkListFragment(cityRedmond.name, STORE_JOY_POSITION, storeJoy)
        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openDetailsFromListInDualMode() {
        switchFromSingleToDualScreen()

        clickOnMapMarker(cityRedmond.name)
        clickOnListItemAtPosition(STORE_JOY_POSITION)
        checkMapFragment()
        checkDetailsFragment(storeJoy)

        navigateUp()
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_JOY_POSITION, storeJoy)
        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }
}
