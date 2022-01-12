/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.order.usecases.GetCurrentOrderUseCase
import com.microsoft.device.samples.dualscreenexperience.domain.order.usecases.GetOrderByIdUseCase
import com.microsoft.device.samples.dualscreenexperience.domain.order.usecases.SubmitOrderUseCase
import com.microsoft.device.samples.dualscreenexperience.domain.order.usecases.UpdateItemQuantityUseCase
import com.microsoft.device.samples.dualscreenexperience.presentation.util.ItemClickListener
import com.microsoft.device.samples.dualscreenexperience.presentation.util.QuantityDataListHandler
import com.microsoft.device.samples.dualscreenexperience.presentation.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    getOrderUseCase: GetCurrentOrderUseCase,
    private val updateItemQuantityUseCase: UpdateItemQuantityUseCase,
    private val submitOrderUseCase: SubmitOrderUseCase,
    private val getOrderByIdUseCase: GetOrderByIdUseCase,
    private val orderNavigator: OrderNavigator
) : ViewModel(), ItemClickListener<Boolean> {

    var itemList: LiveData<List<OrderItem>> = getOrderUseCase.get()
    var submittedOrder = SingleLiveEvent<Order?>(null)
    var showSuccessMessage = false
    var showSignDialog = MutableLiveData(false)

    val quantityDataListHandler = object : QuantityDataListHandler<OrderItem> {
        override fun getDataList(): List<OrderItem>? = getOrderItemDataList()

        override fun updateQuantity(model: OrderItem?, newValue: Int) =
            updateItemQuantity(model?.itemId, newValue)
    }

    val submittedDataListHandler = object : QuantityDataListHandler<OrderItem> {
        override fun getDataList(): List<OrderItem>? = submittedOrder.value?.items

        override fun updateQuantity(model: OrderItem?, newValue: Int) {}
    }

    fun updateItemQuantity(itemId: Long?, newValue: Int) {
        itemId?.let {
            viewModelScope.launch {
                updateItemQuantityUseCase.update(it, newValue)
            }
        }
    }

    override fun onClick(model: Boolean?) {
        if (model == true) {
            showSignDialog.value = true
        }
    }

    fun submitOrder() {
        viewModelScope.launch {
            submitOrderUseCase.submit()?.let {
                submittedOrder.value = getOrderByIdUseCase.get(it)
                showSuccessMessage = true
            }
        }
    }

    fun navigateToReceipt() {
        orderNavigator.navigateToOrderReceipt()
    }

    fun navigateToOrder() {
        orderNavigator.navigateUp()
        submittedOrder.value = null
    }

    fun reset() {
        submittedOrder.value = null
    }

    fun getOrderItemDataList(): List<OrderItem>? = itemList.value
}
