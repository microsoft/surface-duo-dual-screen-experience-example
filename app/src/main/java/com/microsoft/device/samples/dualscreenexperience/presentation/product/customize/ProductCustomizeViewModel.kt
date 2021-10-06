/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.product.customize

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.order.usecases.AddItemToOrderUseCase
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.GuitarType
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.Product
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductColor
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductType
import com.microsoft.device.samples.dualscreenexperience.domain.product.usecases.GetProductByIdUseCase
import com.microsoft.device.samples.dualscreenexperience.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductCustomizeViewModel @Inject constructor(
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addItemUseCase: AddItemToOrderUseCase
) : ViewModel() {
    val customizedProduct = MutableLiveData<Product?>()
    val selectedBodyShape = SingleLiveEvent<ProductType?>(null)
    val selectedBodyColor = MutableLiveData<ProductColor?>(null)
    val selectedGuitarType = MutableLiveData<GuitarType?>(null)

    fun initCustomizedProduct(productId: Long) {
        viewModelScope.launch {
            getProductByIdUseCase.getById(productId)?.let {
                customizedProduct.value = it
            }
        }
    }

    fun updateOrder() {
        viewModelScope.launch {
            customizedProduct.value?.apply {
                selectedBodyShape.value?.let { bodyShape = it }
                selectedBodyColor.value?.let { color = it }
                selectedGuitarType.value?.let { guitarType = it }
            }?.let { customizedProduct ->
                addItemUseCase.addToOrder(OrderItem(customizedProduct))
            }
        }
    }

    fun reset() {
        customizedProduct.value = null
        selectedBodyShape.value = null
        selectedBodyColor.value = null
        selectedGuitarType.value = null
    }
}
