/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.presentation.order

import androidx.lifecycle.LiveData
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.Order
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.order.model.OrderItem.Companion.DEFAULT_QUANTITY
import com.microsoft.device.samples.dualscreenexperience.presentation.order.OrderListAdapter.Companion.POSITION_RECOMMENDATIONS
import com.microsoft.device.samples.dualscreenexperience.util.blockingValue

open class BaseNavigationOrderTest {

    fun openEmptyOrders(recommendationsSize: Int) {
        navigateToOrdersSection()
        checkEmptyPage()

        scrollOrderToEnd()
        checkOrderRecommendationsPage(recommendationsSize, POSITION_RECOMMENDATIONS)
    }

    fun addItemToOrderAndRemove(
        itemPosition: Int,
        orderDetailsPosition: Int,
        recommendationsPosition: Int,
        emptyRecommendationsSize: Int,
        oneItemRecommendationsSize: Int,
        itemLiveData: LiveData<List<OrderItem>>
    ) {
        navigateToOrdersSection()

        clickOnAddFirstRecommendationItem()

        itemLiveData.blockingValue

        checkOrderHeader()
        checkOrderItemList(itemPosition)
        checkItemQuantity(itemPosition, DEFAULT_QUANTITY)
        scrollOrderToEnd()
        checkOrderDetails(orderDetailsPosition)
        checkOrderRecommendationsPage(oneItemRecommendationsSize, recommendationsPosition)

        clickOnItemRemove(itemPosition)

        checkEmptyPage()
        scrollOrderToEnd()
        checkOrderRecommendationsPage(emptyRecommendationsSize, POSITION_RECOMMENDATIONS)
    }

    fun addItemToOrderAndSubmit(
        itemPosition: Int,
        orderDetailsPosition: Int,
        recommendationsPosition: Int,
        emptyRecommendationsSize: Int,
        itemLiveData: LiveData<List<OrderItem>>,
        submittedOrderLiveData: LiveData<Order?>
    ) {
        navigateToOrdersSection()

        clickOnAddFirstRecommendationItem()

        itemLiveData.blockingValue

        checkOrderHeader()
        checkOrderDetails(orderDetailsPosition)
        checkOrderItemList(itemPosition)
        checkItemQuantity(itemPosition, DEFAULT_QUANTITY)

        clickOnSubmitOrderButton(orderDetailsPosition)

        submittedOrderLiveData.blockingValue

        checkOrderSubmittedDetails()
        checkOrderReceiptItems(itemPosition)
        scrollOrderReceiptToEnd()
        checkOrderReceiptRecommendationsPage(emptyRecommendationsSize, recommendationsPosition)
    }

    fun addItemWithDifferentQuantitiesAndSubmit(
        itemPosition: Int,
        orderDetailsPosition: Int,
        recommendationsPosition: Int,
        emptyRecommendationsSize: Int,
        itemLiveData: LiveData<List<OrderItem>>,
        submittedOrderLiveData: LiveData<Order?>
    ) {
        navigateToOrdersSection()

        clickOnAddFirstRecommendationItem()

        itemLiveData.blockingValue

        checkOrderHeader()
        checkOrderItemList(itemPosition)
        checkOrderDetails(orderDetailsPosition)

        checkItemQuantity(itemPosition, DEFAULT_QUANTITY)

        clickOnItemQuantityPlus(itemPosition)

        checkItemQuantity(itemPosition, DEFAULT_QUANTITY + 1)

        clickOnItemQuantityPlus(itemPosition)
        checkItemQuantity(itemPosition, DEFAULT_QUANTITY + 2)

        clickOnItemQuantityMinus(itemPosition)
        checkItemQuantity(itemPosition, DEFAULT_QUANTITY + 1)

        checkOrderItemList(itemPosition)

        clickOnSubmitOrderButton(orderDetailsPosition)

        submittedOrderLiveData.blockingValue

        checkOrderSubmittedDetails()
        checkOrderReceiptItems(itemPosition)
        scrollOrderReceiptToEnd()
        checkOrderReceiptRecommendationsPage(emptyRecommendationsSize, recommendationsPosition)
    }
}