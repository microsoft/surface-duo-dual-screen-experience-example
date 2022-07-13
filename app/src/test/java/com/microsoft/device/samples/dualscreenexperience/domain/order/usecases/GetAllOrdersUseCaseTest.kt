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
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.firstSubmittedOrder
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.firstSubmittedOrderEntity
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetAllOrdersUseCaseTest {

    private lateinit var getAllOrdersUseCase: GetAllSubmittedOrdersUseCase
    private lateinit var mockRepo: MockOrderDataSource

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockRepo = MockOrderDataSource()
        getAllOrdersUseCase = GetAllSubmittedOrdersUseCase(mockRepo)
    }

    @Test
    fun getEmptyListWhenNoOrders() = runBlocking {
        val resultValue = getAllOrdersUseCase.get().getOrAwaitValue()

        MatcherAssert.assertThat(resultValue, Is.`is`(emptyList()))
    }

    @Test
    fun getEmptyListWhenNoSubmittedOrders() = runBlocking {
        val copyFirstOrderEntity = firstOrderEntity.copy()

        mockRepo.insert(copyFirstOrderEntity)

        val resultValue = getAllOrdersUseCase.get().getOrAwaitValue()

        MatcherAssert.assertThat(resultValue, Is.`is`(emptyList()))
    }

    @Test
    fun getItemWhenSubmittedOrderExists() = runBlocking {
        val copyFirstSubmittedOrderEntity = firstSubmittedOrderEntity.copy()

        mockRepo.insert(copyFirstSubmittedOrderEntity)

        val resultValue = getAllOrdersUseCase.get().getOrAwaitValue()

        MatcherAssert.assertThat(resultValue, Is.`is`(listOf(firstSubmittedOrder)))
    }
}
