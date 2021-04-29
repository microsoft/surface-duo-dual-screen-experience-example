/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.presentation.order

import androidx.lifecycle.LiveData
import com.microsoft.device.display.sampleheroapp.domain.order.model.Order
import com.microsoft.device.display.sampleheroapp.domain.order.model.OrderItem
import com.microsoft.device.display.sampleheroapp.util.blockingValue

open class BaseNavigationOrderTest {

    fun openEmptyOrders(recommendationsSize: Int) {
        openOrdersTab()
        checkEmptyPage()

        scrollOrdersToEnd(2)
        checkRecommendationsPage(recommendationsSize)
    }

    fun addItemToOrderAndRemove(itemPosition: Int, recommendationsSize: Int, itemLiveData: LiveData<List<OrderItem>>) {
        openOrdersTab()

        clickOnAddFirstRecommendationItem()

        itemLiveData.blockingValue

        checkOrderDetails()
        checkOrderItemList(itemPosition, true)
        checkItemQuantity(itemPosition, 1)

        clickOnItemRemove(itemPosition)

        checkEmptyPage()
        scrollOrdersToEnd(2)
        checkRecommendationsPage(recommendationsSize)
    }

    fun addItemToOrderAndSubmit(
        itemPosition: Int,
        itemLiveData: LiveData<List<OrderItem>>,
        submittedOrderLiveData: LiveData<Order?>
    ) {
        openOrdersTab()
        clickOnAddFirstRecommendationItem()

        itemLiveData.blockingValue

        checkOrderDetails()
        checkOrderItemList(itemPosition, true)
        checkItemQuantity(itemPosition, 1)

        clickOnSubmitOrderButton()

        submittedOrderLiveData.blockingValue

        checkOrderSubmittedDetails()
    }

    fun addItemWithDifferentQuantitiesAndSubmit(
        itemPosition: Int,
        itemLiveData: LiveData<List<OrderItem>>,
        submittedOrderLiveData: LiveData<Order?>
    ) {
        openOrdersTab()

        clickOnAddFirstRecommendationItem()

        itemLiveData.blockingValue

        checkOrderDetails()
        checkOrderItemList(itemPosition, true)
        checkItemQuantity(itemPosition, 1)

        clickOnItemQuantityPlus(itemPosition)

        checkItemQuantity(itemPosition, 2)

        clickOnItemQuantityPlus(itemPosition)
        checkItemQuantity(itemPosition, 3)

        clickOnItemQuantityMinus(itemPosition)
        checkItemQuantity(itemPosition, 2)

        checkOrderItemList(itemPosition, true)

        clickOnSubmitOrderButton()

        submittedOrderLiveData.blockingValue

        checkOrderSubmittedDetails()
    }
}
