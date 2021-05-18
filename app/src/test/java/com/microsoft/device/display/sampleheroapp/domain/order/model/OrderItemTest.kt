/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.order.model

import com.microsoft.device.display.sampleheroapp.domain.order.testutil.firstOrderItem
import com.microsoft.device.display.sampleheroapp.domain.order.testutil.firstOrderItemEntity
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.product
import org.hamcrest.core.IsNot.not
import org.junit.Assert.assertThat
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class OrderItemTest {

    @Test
    fun buildFromOrderItemEntity() {
        assertThat(firstOrderItem, iz(OrderItem(firstOrderItemEntity)))
    }

    @Test
    fun buildFromProduct() {
        val copyFirstOrderItemEntity = firstOrderItem.copy()

        assertThat(copyFirstOrderItemEntity, iz(not(OrderItem(product))))

        copyFirstOrderItemEntity.orderParentId = null
        assertThat(copyFirstOrderItemEntity, iz(OrderItem(product)))

        copyFirstOrderItemEntity.quantity = 0
        assertThat(copyFirstOrderItemEntity, iz(not(OrderItem(product))))
    }

    @Test
    fun generateOrderItemEntity() {
        assertThat(firstOrderItemEntity, iz(firstOrderItem.toEntity()))
    }
}
