/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation

import androidx.navigation.DuoNavController
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.presentation.product.ProductNavigator
import com.microsoft.device.display.sampleheroapp.presentation.store.StoreNavigator

class AppNavigator : ProductNavigator, StoreNavigator {
    private var navController: DuoNavController? = null

    fun bind(navController: DuoNavController) {
        this.navController = navController
    }

    fun unbind() {
        this.navController = null
    }

    override fun navigateToProductDetails() {
        if (navController?.currentDestination?.id != R.id.fragment_product_details) {
            navController?.navigate(R.id.action_product_list_to_details)
        }
    }

    override fun navigateToMap() {
        navController?.navigate(R.id.fragment_store_map)
    }

    override fun navigateToStoreList() {
        navController?.navigate(R.id.action_store_map_to_list)
    }

    override fun navigateToStoreDetailsFromList() {
        navController?.navigate(R.id.action_store_list_to_details)
    }

    override fun navigateToStoreDetailsFromMap() {
        navController?.navigate(R.id.action_store_map_to_details)
    }

    override fun navigateUp() {
        navController?.navigateUp()
    }
}
