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
import com.microsoft.device.display.sampleheroapp.domain.order.model.OrderItem
import com.microsoft.device.display.sampleheroapp.domain.order.usecases.AddItemToOrderUseCase
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
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addItemUseCase: AddItemToOrderUseCase
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

    fun updateOrder() {
        viewModelScope.launch {
            selectedProduct.value?.apply {
                selectedBodyShape.value?.let { bodyShape = it }
                selectedBodyColor.value?.let { color = it }
            }?.let { customizedProduct ->
                addItemUseCase.addToOrder(OrderItem(customizedProduct))
            }
        }
    }

    companion object {
        const val SELECTED_PRODUCT_ID = "selected_product_id"
        const val SELECTED_DEFAULT_VALUE = 0L
    }
}
