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
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.firstOrderItemEntity
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class SubmitOrderUseCaseTest {

    private lateinit var submitOrderUseCase: SubmitOrderUseCase
    private lateinit var mockRepo: MockOrderDataSource

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockRepo = MockOrderDataSource()
        submitOrderUseCase = SubmitOrderUseCase(mockRepo)
    }

    @Test
    fun submitOrderIfThereIsACurrentOne() = runBlocking {
        val copyFirstOrderItemEntity = firstOrderItemEntity.copy()
        val copyFirstOrderEntity = firstOrderEntity.copy()

        mockRepo.insert(copyFirstOrderEntity)
        mockRepo.insertItems(copyFirstOrderItemEntity)

        val submittedOrderId = submitOrderUseCase.submit()
        assertThat(submittedOrderId, iz(copyFirstOrderEntity.orderId))

        val result = mockRepo.getById(submittedOrderId!!)!!.order
        assertTrue(result.isSubmitted)
        assertThat(result.totalPrice, iz(copyFirstOrderEntity.totalPrice))
    }

    @Test
    fun doNothingIfAllOrdersAreSubmitted() = runBlocking {
        val copyFirstOrderEntity = firstOrderEntity.copy()

        copyFirstOrderEntity.isSubmitted = true
        mockRepo.insert(copyFirstOrderEntity)

        val submittedOrderId = submitOrderUseCase.submit()
        assertNull(submittedOrderId)

        assertThat(mockRepo.getById(copyFirstOrderEntity.orderId!!)?.order, iz(copyFirstOrderEntity))
    }
}
