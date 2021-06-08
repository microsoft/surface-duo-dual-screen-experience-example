/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.store

interface StoreNavigator {
    fun navigateToStoreList()
    fun navigateToStoreDetailsFromList()
    fun navigateToStoreDetailsFromMap()
    fun navigateUp()
}
