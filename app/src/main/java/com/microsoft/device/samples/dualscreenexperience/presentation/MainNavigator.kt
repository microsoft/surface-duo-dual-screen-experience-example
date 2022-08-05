/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation

import android.util.Log
import androidx.navigation.FoldableNavController
import androidx.navigation.FoldableNavOptions
import com.microsoft.device.dualscreen.navigation.LaunchScreen
import com.microsoft.device.samples.dualscreenexperience.R
import com.microsoft.device.samples.dualscreenexperience.presentation.catalog.CatalogNavigator
import com.microsoft.device.samples.dualscreenexperience.presentation.history.HistoryNavigator
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderNavigator
import com.microsoft.device.samples.dualscreenexperience.presentation.product.ProductNavigator
import com.microsoft.device.samples.dualscreenexperience.presentation.store.StoreNavigator

class MainNavigator : StoreNavigator, CatalogNavigator, ProductNavigator, OrderNavigator, HistoryNavigator {
    private var navController: FoldableNavController? = null

    fun bind(navController: FoldableNavController) {
        this.navController = navController
    }

    fun unbind() {
        this.navController = null
    }

    override fun isNavigationAtStart() =
        navController?.currentDestination?.id == R.id.fragment_store_map

    override fun navigateUp() {
        navController?.navigateUp()
        Log.i("HISTORY_TEST", "navigateUp")
    }

    override fun navigateToStores() {
        val navOptions = FoldableNavOptions.Builder().setLaunchScreen(LaunchScreen.BOTH).build()
        navController?.navigate(R.id.navigation_stores_graph, null, navOptions)
        Log.i("HISTORY_TEST", "navigateToStores")
    }

    override fun navigateToStoreList() {
        navController?.navigate(R.id.action_store_map_to_list)
        Log.i("HISTORY_TEST", "navigateToStoreList")
    }

    override fun navigateToStoreDetailsFromList() {
        navController?.navigate(R.id.action_store_list_to_details)
        Log.i("HISTORY_TEST", "navigateToStoreDetailsFromList")
    }

    override fun navigateToStoreDetailsFromMap() {
        navController?.navigate(R.id.action_store_map_to_details)
        Log.i("HISTORY_TEST", "navigateToStoreDetailsFromMap")
    }

    override fun navigateToStoreListFromDetails() {
        navController?.navigate(R.id.action_store_details_to_list)
        Log.i("HISTORY_TEST", "navigateToStoreListFromDetails")
    }

    override fun navigateToCatalog() {
        val navOptions = FoldableNavOptions.Builder().setLaunchScreen(LaunchScreen.BOTH).build()
        navController?.navigate(R.id.navigation_catalog_graph, null, navOptions)
        Log.i("HISTORY_TEST", "navigateToCatalog")
    }

    override fun navigateToProducts() {
        val navOptions = FoldableNavOptions.Builder().setLaunchScreen(LaunchScreen.START).build()
        navController?.navigate(R.id.navigation_products_graph, null, navOptions)
        Log.i("HISTORY_TEST", "navigateToProducts")
    }

    override fun navigateToProductDetails() {
        navController?.navigate(R.id.action_product_list_to_details)
        Log.i("HISTORY_TEST", "navigateToProductDetails")
    }

    override fun navigateToProductCustomize() {
        navController?.navigate(R.id.action_product_details_to_customize)
        Log.i("HISTORY_TEST", "navigateToProductCustomize")
    }

    override fun navigateToOrders() {
        val navOptions = FoldableNavOptions.Builder().setLaunchScreen(LaunchScreen.BOTH).build()
        navController?.navigate(R.id.navigation_orders_graph, null, navOptions)
        Log.i("HISTORY_TEST", "navigateToOrders")
    }

    override fun navigateToOrderReceipt() {
        navController?.navigate(R.id.action_order_to_receipt)
        Log.i("HISTORY_TEST", "navigateToOrderReceipt")
    }

    override fun navigateToHistory() {
        val navOptions = FoldableNavOptions.Builder().setLaunchScreen(LaunchScreen.BOTH).build()
        navController?.navigate(R.id.navigation_history_graph, null, navOptions)
        Log.i("HISTORY_TEST", "navigateToHistory")
    }

    override fun navigateToHistoryDetails() {
        navController?.navigate(R.id.action_history_list_to_details)
        Log.i("HISTORY_TEST", "navigateToHistoryDetails")
    }
}
