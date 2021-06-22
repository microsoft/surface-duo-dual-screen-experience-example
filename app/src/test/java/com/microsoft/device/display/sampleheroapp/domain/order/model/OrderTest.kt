/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.order.model

import com.microsoft.device.display.sampleheroapp.domain.order.testutil.firstOrder
import com.microsoft.device.display.sampleheroapp.domain.order.testutil.firstOrderItem
import com.microsoft.device.display.sampleheroapp.domain.order.testutil.orderWithItems
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class OrderTest {

    @Test
    fun buildFromOrderEntity() {
        val copyFirstOrder = firstOrder.copy()
        val copyFirstOrderItem = firstOrderItem.copy()

        copyFirstOrder.items.add(copyFirstOrderItem)
        assertThat(copyFirstOrder, iz(Order(orderWithItems)))
        copyFirstOrder.items.clear()
    }
}
