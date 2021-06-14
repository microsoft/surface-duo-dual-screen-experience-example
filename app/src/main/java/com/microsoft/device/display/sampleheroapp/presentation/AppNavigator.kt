/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation

import androidx.navigation.SurfaceDuoNavController
import androidx.navigation.SurfaceDuoNavOptions
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.presentation.order.OrderNavigator
import com.microsoft.device.display.sampleheroapp.presentation.product.ProductNavigator
import com.microsoft.device.display.sampleheroapp.presentation.store.StoreNavigator
import com.microsoft.device.dualscreen.navigation.LaunchScreen

class AppNavigator : ProductNavigator, StoreNavigator, OrderNavigator {
    private var navController: SurfaceDuoNavController? = null

    fun bind(navController: SurfaceDuoNavController) {
        this.navController = navController
    }

    fun unbind() {
        this.navController = null
    }

    fun isNavigationAtStart() =
        navController?.currentDestination?.id == R.id.fragment_store_map

    override fun navigateUp() {
        navController?.navigateUp()
    }

    override fun navigateToStores() {
        val navOptions = SurfaceDuoNavOptions.Builder().setLaunchScreen(LaunchScreen.START).build()
        navController?.navigate(R.id.navigation_stores_graph, null, navOptions)
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

    override fun navigateToProducts() {
        val navOptions = SurfaceDuoNavOptions.Builder().setLaunchScreen(LaunchScreen.START).build()
        navController?.navigate(R.id.navigation_products_graph, null, navOptions)
    }

    override fun navigateToProductDetails() {
        navController?.navigate(R.id.action_product_list_to_details)
    }

    override fun navigateToProductCustomize() {
        navController?.navigate(R.id.action_product_details_to_customize)
    }

    override fun navigateToOrders() {
        val navOptions = SurfaceDuoNavOptions.Builder().setLaunchScreen(LaunchScreen.START).build()
        navController?.navigate(R.id.navigation_orders_graph, null, navOptions)
    }

    override fun navigateToOrderReceipt() {
        navController?.navigate(R.id.action_order_to_receipt)
    }
}
