/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation

import android.os.Handler
import android.os.Looper
import androidx.navigation.SurfaceDuoNavController
import com.microsoft.device.display.sampleheroapp.R
import com.microsoft.device.display.sampleheroapp.presentation.order.OrderNavigator
import com.microsoft.device.display.sampleheroapp.presentation.product.ProductNavigator
import com.microsoft.device.display.sampleheroapp.presentation.store.StoreNavigator

class AppNavigator : ProductNavigator, StoreNavigator, OrderNavigator {
    private var navController: SurfaceDuoNavController? = null
    fun bind(navController: SurfaceDuoNavController) {
        this.navController = navController
    }

    fun unbind() {
        this.navController = null
    }

    override fun navigateToProductDetails() {
        if (navController?.currentDestination?.id != R.id.fragment_product_details) {
            Looper.myLooper()?.let {
                Handler(it).post {
                    navController?.navigate(R.id.action_product_list_to_details)
                }
            }
        }
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

    override fun navigateToReceipt() {
        if (navController?.currentDestination?.id != R.id.fragment_order_receipt) {
            navController?.navigate(R.id.action_order_to_receipt)
        }
    }

    override fun navigateToOrder() {
        navController?.navigateUp()
    }
}
