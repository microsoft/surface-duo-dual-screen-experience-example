/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.order.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.microsoft.device.display.sampleheroapp.domain.order.testutil.MockOrderDataSource
import com.microsoft.device.display.sampleheroapp.domain.order.testutil.firstOrderEntity
import com.microsoft.device.display.sampleheroapp.domain.order.testutil.firstOrderItem
import com.microsoft.device.display.sampleheroapp.domain.order.testutil.firstOrderItemEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThat
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

        addItemToOrderUseCase.addToOrder(firstOrderItem)

        copyFirstOrderItemEntity.itemId = mockRepo.autogeneratedItemId

        val result = mockRepo.getAll()
        assertThat(result.size, iz(1))
        assertThat(result[0].items, iz(listOf(copyFirstOrderItemEntity)))
        assertThat(result[0].order.isSubmitted, iz(false))
        assertThat(result[0].order.totalPrice, iz(0))
    }

    @Test
    fun addToOrderWhenThereIsACurrentOrder() = runBlocking {
        val copyFirstOrderItemEntity = firstOrderItemEntity.copy()
        val copyFirstOrderEntity = firstOrderEntity.copy()

        mockRepo.insert(copyFirstOrderEntity)

        addItemToOrderUseCase.addToOrder(firstOrderItem)

        copyFirstOrderItemEntity.itemId = mockRepo.autogeneratedItemId

        val result = mockRepo.getAll()
        assertThat(result.size, iz(1))
        assertThat(result[0].items, iz(listOf(copyFirstOrderItemEntity)))
        assertThat(result[0].order.isSubmitted, iz(false))
        assertThat(result[0].order.totalPrice, iz(copyFirstOrderEntity.totalPrice))
        assertThat(result[0].order.orderTimestamp, iz(copyFirstOrderEntity.orderTimestamp))
    }
}