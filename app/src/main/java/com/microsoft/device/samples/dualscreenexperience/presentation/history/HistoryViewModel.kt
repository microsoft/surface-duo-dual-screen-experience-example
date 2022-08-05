/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.order.usecases.AddItemToOrderUseCase
import com.microsoft.device.samples.dualscreenexperience.domain.order.usecases.GetAllSubmittedOrdersUseCase
import com.microsoft.device.samples.dualscreenexperience.presentation.util.logFunctionCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    getAllOrdersUseCase: GetAllSubmittedOrdersUseCase,
    private val addItemUseCase: AddItemToOrderUseCase,
    private val navigator: HistoryNavigator
) : ViewModel() {
    var orderList: LiveData<List<Order>> = getAllOrdersUseCase.get()
    var selectedOrder = MutableLiveData<Order?>(null)

    fun reset() {
        selectedOrder.value = null
        this.javaClass.logFunctionCall("reset")
    }

    fun onClick(model: Order) {
        navigateToDetails()
        selectOrder(model)
    }

    fun navigateUp() {
        navigator.navigateUp()
    }

    fun navigateToDetails() {
        navigator.navigateToHistoryDetails()
    }

    fun selectMostRecentOrder() {
        orderList.value?.takeIf { it.isNotEmpty() && selectedOrder.value == null }?.let { list ->
            selectOrder(list[list.size - 1])
        }
    }

    fun selectOrder(order: Order) {
        selectedOrder.value = order
    }

    fun addItemToOrder(item: OrderItem) {
        viewModelScope.launch {
            // Add copy of previous order item to new order
            addItemUseCase.addToOrder(item.copy())
        }
    }
}
