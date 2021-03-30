/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.product.customize

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.display.sampleheroapp.domain.product.model.Product
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductColor
import com.microsoft.device.display.sampleheroapp.domain.product.model.ProductType
import com.microsoft.device.display.sampleheroapp.domain.product.usecases.GetProductByIdUseCase
import com.microsoft.device.display.sampleheroapp.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductCustomizeViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase
) : ViewModel() {
    val selectedProduct = MutableLiveData<Product?>()
    val selectedBodyShape = SingleLiveEvent<ProductType?>(null)
    val selectedBodyColor = MutableLiveData<ProductColor?>(null)

    fun initSelectedProduct(selectedId: Long) {
        viewModelScope.launch {
            getProductByIdUseCase.getById(selectedId)?.let {
                selectedProduct.value = it
            }
        }
    }

    companion object {
        const val SELECTED_PRODUCT_ID = "selected_product_id"
        const val SELECTED_DEFAULT_VALUE = 0L
    }
}
