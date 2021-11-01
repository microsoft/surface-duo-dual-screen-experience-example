/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.order.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.MockOrderDataSource
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.firstOrderEntity
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.firstOrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.firstOrderItemEntity
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.secondOrderItem
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.secondOrderItemEntity
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class AddItemToOrderUseCaseTest {

    private lateinit var addItemToOrderUseCase: AddItemToOrderUseCase
    private lateinit var mockRepo: MockOrderDataSource

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockRepo = MockOrderDataSource()
        addItemToOrderUseCase = AddItemToOrderUseCase(mockRepo)
    }

    @Test
    fun addToOrderWhenAllOrdersAreSubmitted() = runBlocking {
        val copyFirstOrderItemEntity = firstOrderItemEntity.copy()
        val copyFirstOrderItem = firstOrderItem.copy()

        addItemToOrderUseCase.addToOrder(copyFirstOrderItem)

        copyFirstOrderItemEntity.itemId = mockRepo.autogeneratedItemId

        val result = mockRepo.getAll()
        assertThat(result.size, iz(1))
        assertThat(result[0].items, iz(listOf(copyFirstOrderItemEntity)))
        assertThat(result[0].order.isSubmitted, iz(false))
        assertThat(result[0].order.totalPrice, iz(0))
    }

    @Test
    fun addToOrderAndInsertItemWhenThereIsACurrentOrder() = runBlocking {
        val copyFirstOrderItemEntity = firstOrderItemEntity.copy()
        val copyFirstOrderEntity = firstOrderEntity.copy()
        val copyFirstOrderItem = firstOrderItem.copy()

        mockRepo.insert(copyFirstOrderEntity)

        addItemToOrderUseCase.addToOrder(copyFirstOrderItem)

        copyFirstOrderItemEntity.itemId = mockRepo.autogeneratedItemId

        val result = mockRepo.getAll()
        assertThat(result.size, iz(1))
        assertThat(result[0].items, iz(listOf(copyFirstOrderItemEntity)))
        assertThat(result[0].order.isSubmitted, iz(false))
        assertThat(result[0].order.totalPrice, iz(copyFirstOrderEntity.totalPrice))
        assertThat(result[0].order.orderTimestamp, iz(copyFirstOrderEntity.orderTimestamp))

        val copySecondOrderItemEntity = secondOrderItemEntity.copy()
        val copySecondOrderItem = secondOrderItem.copy()

        addItemToOrderUseCase.addToOrder(copySecondOrderItem)

        copySecondOrderItemEntity.itemId = mockRepo.autogeneratedItemId

        val resultTwo = mockRepo.getAll()
        assertThat(resultTwo.size, iz(1))
        assertThat(resultTwo[0].items, iz(listOf(copyFirstOrderItemEntity, copySecondOrderItemEntity)))
        assertThat(resultTwo[0].order.isSubmitted, iz(false))
        assertThat(resultTwo[0].order.totalPrice, iz(copyFirstOrderEntity.totalPrice))
        assertThat(resultTwo[0].order.orderTimestamp, iz(copyFirstOrderEntity.orderTimestamp))
    }

    @Test
    fun addToOrderAndUpdateQuantityWhenThereIsACurrentOrder() = runBlocking {
        val copyFirstOrderItemEntity = firstOrderItemEntity.copy()
        val copyFirstOrderEntity = firstOrderEntity.copy()
        val copyFirstOrderItem = firstOrderItem.copy()

        mockRepo.insert(copyFirstOrderEntity)

        addItemToOrderUseCase.addToOrder(copyFirstOrderItem)

        copyFirstOrderItemEntity.itemId = mockRepo.autogeneratedItemId

        val result = mockRepo.getAll()
        assertThat(result.size, iz(1))
        assertThat(result[0].items, iz(listOf(copyFirstOrderItemEntity)))
        assertThat(result[0].order.isSubmitted, iz(false))
        assertThat(result[0].order.totalPrice, iz(copyFirstOrderEntity.totalPrice))
        assertThat(result[0].order.orderTimestamp, iz(copyFirstOrderEntity.orderTimestamp))

        val copySecondOrderItemEntity = firstOrderItemEntity.copy()
        val copySecondOrderItem = firstOrderItem.copy(itemId = secondOrderItem.itemId)

        val resultOrderItemEntity = copyFirstOrderItemEntity.copy(quantity = 2)

        addItemToOrderUseCase.addToOrder(copySecondOrderItem)

        copySecondOrderItemEntity.itemId = mockRepo.autogeneratedItemId

        val resultTwo = mockRepo.getAll()
        assertThat(resultTwo.size, iz(1))
        assertThat(resultTwo[0].items, iz(listOf(resultOrderItemEntity)))
        assertThat(resultTwo[0].order.isSubmitted, iz(false))
        assertThat(resultTwo[0].order.totalPrice, iz(copyFirstOrderEntity.totalPrice))
        assertThat(resultTwo[0].order.orderTimestamp, iz(copyFirstOrderEntity.orderTimestamp))
    }
}
