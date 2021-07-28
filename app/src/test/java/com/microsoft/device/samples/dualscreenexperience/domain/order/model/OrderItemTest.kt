/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.order.model

import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.firstOrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.firstOrderItemEntity
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.product
import com.microsoft.device.samples.dualscreenexperience.domain.product.testutil.secondProduct
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsNot.not
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class OrderItemTest {

    @Test
    fun buildFromOrderItemEntity() {
        assertThat(firstOrderItem, iz(OrderItem(firstOrderItemEntity)))
    }

    @Test
    fun buildFromProduct() {
        val copyFirstOrderItem = firstOrderItem.copy()

        assertThat(copyFirstOrderItem, iz(not(OrderItem(product))))

        copyFirstOrderItem.orderParentId = null
        assertThat(copyFirstOrderItem, iz(OrderItem(product)))

        copyFirstOrderItem.quantity = 0
        assertThat(copyFirstOrderItem, iz(not(OrderItem(product))))
    }

    @Test
    fun generateOrderItemEntity() {
        assertThat(firstOrderItemEntity, iz(firstOrderItem.toEntity()))
    }

    @Test
    fun checkEquals() {
        val copyFirstOrderItem = firstOrderItem.copy()

        assertTrue(copyFirstOrderItem == copyFirstOrderItem)

        val copyIdOrderItem = firstOrderItem.copy(itemId = copyFirstOrderItem.itemId?.inc())

        assertTrue(copyIdOrderItem == copyFirstOrderItem)

        val copyBodyOrderItem = firstOrderItem.copy(bodyShape = secondProduct.bodyShape)

        assertFalse(copyBodyOrderItem == copyFirstOrderItem)

        val copyColorOrderItem = firstOrderItem.copy(color = secondProduct.color)

        assertFalse(copyColorOrderItem == copyFirstOrderItem)

        val copyNameOrderItem = firstOrderItem.copy(name = secondProduct.name)

        assertFalse(copyNameOrderItem == copyFirstOrderItem)

        val copyPriceOrderItem = firstOrderItem.copy(price = secondProduct.price)

        assertFalse(copyPriceOrderItem == copyFirstOrderItem)
    }
}
