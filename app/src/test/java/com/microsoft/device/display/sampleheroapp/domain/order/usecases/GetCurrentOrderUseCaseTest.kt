/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.order.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.microsoft.device.display.sampleheroapp.domain.order.testutil.MockOrderDataSource
import com.microsoft.device.display.sampleheroapp.domain.order.testutil.blockingValue
import com.microsoft.device.display.sampleheroapp.domain.order.testutil.firstOrderEntity
import com.microsoft.device.display.sampleheroapp.domain.order.testutil.firstOrderItem
import com.microsoft.device.display.sampleheroapp.domain.order.testutil.firstOrderItemEntity
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class GetCurrentOrderUseCaseTest {
    private lateinit var getCurrentOrderUseCase: GetCurrentOrderUseCase
    private lateinit var mockRepo: MockOrderDataSource

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockRepo = MockOrderDataSource()
        getCurrentOrderUseCase = GetCurrentOrderUseCase(mockRepo)
    }

    @Test
    fun getNullWhenOrderIdDoesNotExist() = runBlocking {
        val result = getCurrentOrderUseCase.get().blockingValue

        assertNull(result)
    }

    @Test
    fun getEmptyListWhenOrderIdExists() = runBlocking {
        val copyFirstOrderEntity = firstOrderEntity.copy()

        mockRepo.insert(copyFirstOrderEntity)

        val resultValue = getCurrentOrderUseCase.get().blockingValue

        assertThat(resultValue, iz(emptyList()))
    }

    @Test
    fun getItemWhenOrderIdExists() = runBlocking {
        val copyFirstOrderItemEntity = firstOrderItemEntity.copy()
        val copyFirstOrderEntity = firstOrderEntity.copy()

        mockRepo.insert(copyFirstOrderEntity)
        mockRepo.insertItems(copyFirstOrderItemEntity)

        val resultValue = getCurrentOrderUseCase.get().blockingValue
        val expectedItem = firstOrderItem.copy(itemId = copyFirstOrderItemEntity.itemId)

        assertThat(resultValue, iz(listOf(expectedItem)))
    }
}
