/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.store

interface StoreNavigator {
    fun navigateToStores()
    fun navigateToStoreList()
    fun navigateToStoreDetailsFromList()
    fun navigateToStoreDetailsFromMap()
    fun navigateToStoreListFromDetails()
    fun navigateUp()
}
