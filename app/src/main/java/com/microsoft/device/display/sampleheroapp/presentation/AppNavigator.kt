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

class AppNavigator : ProductNavigator {
    private var navController: DuoNavController? = null

    fun bind(navController: DuoNavController) {
        this.navController = navController
    }

    fun unbind() {
        this.navController = null
    }

    override fun navigateToDetails() {
        navController?.navigate(R.id.action_product_list_to_detail)
    }

    fun navigateUp() {
        navController?.navigateUp()
    }
}
