/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.order.usecases

import com.microsoft.device.samples.dualscreenexperience.data.order.OrderDataSource
import javax.inject.Inject

class UpdateItemQuantityUseCase @Inject constructor(private val orderRepository: OrderDataSource) {
    suspend fun update(itemId: Long, newQuantity: Int) {
        if (newQuantity != 0) {
            orderRepository.updateItem(itemId, newQuantity)
        } else {
            orderRepository.deleteItem(itemId)
        }
    }
}
