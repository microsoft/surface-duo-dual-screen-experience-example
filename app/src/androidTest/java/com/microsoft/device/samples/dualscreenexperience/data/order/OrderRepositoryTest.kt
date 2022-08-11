/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.order

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.device.samples.dualscreenexperience.data.AppDatabase
import com.microsoft.device.samples.dualscreenexperience.data.order.local.OrderLocalDataSource
import com.microsoft.device.samples.dualscreenexperience.data.order.model.OrderWithItems
import com.microsoft.device.samples.dualscreenexperience.util.getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.core.Is.`is` as iz

@RunWith(AndroidJUnit4ClassRunner::class)
class OrderRepositoryTest {

    private val orderWithItems = OrderWithItems(firstOrderEntity, mutableListOf(firstOrderItemEntity))
    private val orderWithoutItems = OrderWithItems(firstOrderEntity, mutableListOf())
    private val submittedOrderWithoutItems = OrderWithItems(firstSubmittedOrderEntity, mutableListOf())

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var database: AppDatabase
    private lateinit var orderRepo: OrderRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDatabase() {
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        orderRepo = OrderRepository(OrderLocalDataSource(database.orderDao()))
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertAndGetOrder() = runBlocking {
        assertThat(orderRepo.getAll(), iz(emptyList()))
        assertNull(orderRepo.getById(firstOrderEntity.orderId!!))

        orderRepo.insert(firstOrderEntity)
        val result = orderRepo.getAll()

        assertThat(result, iz(not(Matchers.empty())))
        assertThat(result, iz(listOf(orderWithoutItems)))
        assertThat(orderRepo.getById(firstOrderEntity.orderId!!), iz(orderWithoutItems))
    }

    @Test
    fun insertItemsAndGetOrder() = runBlocking {
        assertThat(orderRepo.getAll(), iz(emptyList()))

        orderRepo.insert(firstOrderEntity)
        orderRepo.insertItems(firstOrderItemEntity)
        val result = orderRepo.getAll()

        assertThat(result, iz(not(Matchers.empty())))
        assertThat(result, iz(listOf(orderWithItems)))
    }

    @Test
    fun getCurrentOrder() = runBlocking {
        var result = orderRepo.getOrderBySubmitted(false).getOrAwaitValue()

        assertNull(result)

        orderRepo.insert(firstOrderEntity)
        result = orderRepo.getOrderBySubmitted(false).getOrAwaitValue()

        assertThat(result, iz(orderWithoutItems))

        orderRepo.insertItems(firstOrderItemEntity)
        result = orderRepo.getOrderBySubmitted(false).getOrAwaitValue()

        assertThat(result, iz(orderWithItems))
    }

    @Test
    fun getAllSubmittedOrders() = runBlocking {
        var result = orderRepo.getAllSubmittedOrders().getOrAwaitValue()

        assertThat(result, iz(Matchers.empty()))

        orderRepo.insert(firstOrderEntity)
        result = orderRepo.getAllSubmittedOrders().getOrAwaitValue()

        assertThat(result, iz(Matchers.empty()))

        orderRepo.insert(firstSubmittedOrderEntity)
        result = orderRepo.getAllSubmittedOrders().getOrAwaitValue()

        assertThat(result, iz(listOf(submittedOrderWithoutItems)))
    }
}
