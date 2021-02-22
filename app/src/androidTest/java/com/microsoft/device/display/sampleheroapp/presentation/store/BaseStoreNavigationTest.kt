/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store

import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.util.switchFromSingleToDualScreen

open class BaseStoreNavigationTest {

    fun openMap_inSingleMode() {
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openDetailsFromMap_inSingleMode() {
        clickOnMapMarker(storeMehul.name)
        checkDetailsFragment(storeMehul)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openDetailsFromMap_inDualMode() {
        switchFromSingleToDualScreen()

        clickOnMapMarker(storeMehul.name)
        checkMapFragment()
        checkDetailsFragment(storeMehul)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openListFromMap_inSingleMode() {
        clickOnMapMarker(cityRedmond.name)
        checkListFragment(cityRedmond.name, STORE_JOY_POSITION, storeJoy)

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openListFromMap_inDualMode() {
        switchFromSingleToDualScreen()

        clickOnMapMarker(cityRedmond.name)
        checkMapFragment()
        checkListFragment(cityRedmond.name, STORE_JOY_POSITION, storeJoy)
        checkListFragmentInEmptyState()

        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openDetailsFromList_inSingleMode() {
        clickOnMapMarker(cityRedmond.name)
        clickOnListItemAtPosition(STORE_JOY_POSITION)
        checkDetailsFragment(storeJoy)

        navigateUp()
        checkListFragment(cityRedmond.name, STORE_JOY_POSITION, storeJoy)
        navigateUp()
        checkMapFragment()
        checkToolbar(R.string.app_name)
    }

    fun openDetailsFromList_inDualMode() {
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
