/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductColor
import com.microsoft.device.samples.dualscreenexperience.domain.product.model.ProductType
import com.microsoft.device.samples.dualscreenexperience.presentation.util.DataListHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    // TODO: add use case
    private val navigator: HistoryNavigator
) : ViewModel(), DataListHandler<Order> {
    var orderList = MutableLiveData<List<Order>?>(null)
    var selectedOrder = MutableLiveData<Order?>(null)

    init {
        viewModelScope.launch {
            // REVISIT: pull from actual order history data
            orderList.value = listOf(
                Order(
                    12346L,
                    1655103423L,
                    16377,
                    true,
                    mutableListOf(
                        OrderItem(
                            itemId = 12526L,
                            orderParentId = 12346L,
                            name = "test name 1",
                            price = 667,
                            bodyShape = ProductType.CLASSIC,
                            color = ProductColor.BLUE,
                            quantity = 2
                        ),
                        OrderItem(
                            itemId = 1256L,
                            orderParentId = 12346L,
                            name = "test name 2",
                            price = 125,
                            bodyShape = ProductType.ROCK,
                            color = ProductColor.DARK_RED,
                            quantity = 1
                        ),
                        OrderItem(
                            itemId = 156L,
                            orderParentId = 12346L,
                            name = "test name 3",
                            price = 12,
                            bodyShape = ProductType.ELECTRIC,
                            color = ProductColor.ORANGE,
                            quantity = 1
                        )
                    )
                ),
                Order(
                    12345L,
                    1655140123L,
                    12345,
                    true,
                    mutableListOf(
                        OrderItem(
                            itemId = 123456L,
                            orderParentId = 12345L,
                            name = "test name 2",
                            price = 12345,
                            bodyShape = ProductType.ROCK,
                            color = ProductColor.DARK_RED,
                            quantity = 1
                        )
                    )
                )
            )
        }
    }

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
        orderList.value?.takeIf { selectedOrder.value == null }?.let { list ->
            selectOrder(list[0])
        }
    }

    private fun selectOrder(order: Order?) {
        selectedOrder.value = order
    }
}
