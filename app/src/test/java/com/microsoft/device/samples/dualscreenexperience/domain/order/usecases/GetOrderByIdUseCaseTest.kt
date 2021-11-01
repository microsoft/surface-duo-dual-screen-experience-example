/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.order.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.MockOrderDataSource
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.firstOrder
import com.microsoft.device.samples.dualscreenexperience.domain.order.testutil.firstOrderEntity
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class GetOrderByIdUseCaseTest {

    private lateinit var getOrderByIdUseCase: GetOrderByIdUseCase
    private lateinit var mockRepo: MockOrderDataSource

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockRepo = MockOrderDataSource()
        getOrderByIdUseCase = GetOrderByIdUseCase(mockRepo)
    }

    @Test
    fun getNullWhenIdDoesNotExist() = runBlocking {
        assertNull(getOrderByIdUseCase.get(-1))
    }

    @Test
    fun getOrderWhenIdExists() = runBlocking {
        val copyFirstOrderEntity = firstOrderEntity.copy()

        mockRepo.insert(copyFirstOrderEntity)
        assertThat(firstOrder, iz(getOrderByIdUseCase.get(copyFirstOrderEntity.orderId!!)))
    }
}
