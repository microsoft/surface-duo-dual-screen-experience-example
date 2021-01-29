/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.display.sampleheroapp.domain.product.model.Product
import com.microsoft.device.display.sampleheroapp.domain.product.usecases.GetProductsUseCase
import com.microsoft.device.display.sampleheroapp.presentation.util.ItemClickListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val navigator: ProductNavigator
) : ViewModel(), ItemClickListener<Product> {
    var productList = MutableLiveData<List<Product>?>(null)
    val selectedProduct = MutableLiveData<Product?>(null)

    init {
        viewModelScope.launch {
            productList.value = getProductsUseCase.getAll()
        }
    }

    override fun onClick(model: Product?) {
        this.selectedProduct.value = model
        navigator.navigateToDetails()
    }
}
