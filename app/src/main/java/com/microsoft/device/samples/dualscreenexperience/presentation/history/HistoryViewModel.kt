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
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import com.microsoft.device.samples.dualscreenexperience.domain.order.usecases.GetAllOrdersUseCase
import com.microsoft.device.samples.dualscreenexperience.presentation.util.DataListHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    getAllOrdersUseCase: GetAllOrdersUseCase,
    private val navigator: HistoryNavigator
) : ViewModel(), DataListHandler<Order> {
    var orderList: LiveData<List<Order>> = getAllOrdersUseCase.get()
    var selectedOrder = MutableLiveData<Order?>(null)

    fun reset() {
        selectedOrder.value = null
    }

    override fun getDataList(): List<Order>? = orderList.value

    override fun onClick(model: Order?) {
        navigateToDetails()
        selectOrder(model)
    }

    fun navigateUp() {
        navigator.navigateUp()
    }

    fun navigateToDetails() {
        navigator.navigateToHistoryDetails()
    }

    fun selectFirstOrder() {
        orderList.value?.takeIf { it.isNotEmpty() && selectedOrder.value == null }?.let { list ->
            selectOrder(list[0])
        }
    }

    private fun selectOrder(order: Order?) {
        selectedOrder.value = order
    }
}
