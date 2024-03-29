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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class UpdateItemQuantityUseCaseTest {

    private lateinit var updateItemQuantityUseCase: UpdateItemQuantityUseCase
    private lateinit var mockRepo: MockOrderDataSource

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val newQuantity = 5

    @Before
    fun setup() {
        mockRepo = MockOrderDataSource()
        updateItemQuantityUseCase = UpdateItemQuantityUseCase(mockRepo)
    }

    @Test
    fun deleteItemIfQuantityIsZero() = runBlocking {
        val copyFirstOrderItemEntity = firstOrderItemEntity.copy()
        val copyFirstOrderEntity = firstOrderEntity.copy()

        mockRepo.insert(copyFirstOrderEntity)
        mockRepo.insertItems(copyFirstOrderItemEntity)

        assertThat(mockRepo.getById(copyFirstOrderEntity.orderId!!)?.items?.size, iz(1))

        updateItemQuantityUseCase.update(mockRepo.autogeneratedItemId, 0)
        assertThat(mockRepo.getById(copyFirstOrderEntity.orderId!!)?.items?.size, iz(0))
    }

    @Test
    fun updateItemIfQuantityIsNotZero() = runBlocking {
        val copyFirstOrderItemEntity = firstOrderItemEntity.copy()
        val copyFirstOrderEntity = firstOrderEntity.copy()

        mockRepo.insert(copyFirstOrderEntity)
        mockRepo.insertItems(copyFirstOrderItemEntity)

        updateItemQuantityUseCase.update(mockRepo.autogeneratedItemId, newQuantity)
        assertThat(mockRepo.getById(copyFirstOrderEntity.orderId!!)?.items?.get(0)?.quantity, iz(newQuantity))
    }
}
