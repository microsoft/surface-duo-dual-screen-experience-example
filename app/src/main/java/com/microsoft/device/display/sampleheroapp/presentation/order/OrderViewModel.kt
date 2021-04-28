/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.display.sampleheroapp.domain.order.model.Order
import com.microsoft.device.display.sampleheroapp.domain.order.model.OrderItem
import com.microsoft.device.display.sampleheroapp.domain.order.usecases.GetCurrentOrderUseCase
import com.microsoft.device.display.sampleheroapp.domain.order.usecases.GetOrderByIdUseCase
import com.microsoft.device.display.sampleheroapp.domain.order.usecases.SubmitOrderUseCase
import com.microsoft.device.display.sampleheroapp.domain.order.usecases.UpdateItemQuantityUseCase
import com.microsoft.device.display.sampleheroapp.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val getOrderUseCase: GetCurrentOrderUseCase,
    private val updateItemQuantityUseCase: UpdateItemQuantityUseCase,
    private val submitOrderUseCase: SubmitOrderUseCase,
    private val getOrderByIdUseCase: GetOrderByIdUseCase
) : ViewModel() {

    lateinit var itemList: LiveData<List<OrderItem>>
    var submittedOrder = MutableLiveData<Order?>(null)
    var showSuccessMessage = SingleLiveEvent<Boolean?>(null)

    fun resetItemList() {
        itemList = getOrderUseCase.get()
        submittedOrder.value = null
    }

    fun updateItemQuantity(itemId: Long?, newValue: Int) {
        itemId?.let {
            viewModelScope.launch {
                updateItemQuantityUseCase.update(it, newValue)
            }
        }
    }

    fun submitOrder() {
        showSuccessMessage.value = true
        viewModelScope.launch {
            itemList = MutableLiveData(itemList.value)
            submitOrderUseCase.submit()?.let {
                submittedOrder.value = getOrderByIdUseCase.get(it)
            }
        }
    }

    fun getDataList(): List<OrderItem>? = itemList.value

    fun getCount(orderItemList: List<OrderItem>?) =
        orderItemList?.sumOf { it.quantity } ?: 0

    fun getTotalPrice(orderItemList: List<OrderItem>?) =
        orderItemList?.sumOf { it.price * it.quantity }?.toFloat() ?: 0f
}
