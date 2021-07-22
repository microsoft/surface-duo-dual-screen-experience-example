/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.data.product

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.device.samples.dualscreenexperience.data.AppDatabase
import com.microsoft.device.samples.dualscreenexperience.data.product.local.ProductLocalDataSource
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.empty
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.core.Is.`is` as iz

@RunWith(AndroidJUnit4ClassRunner::class)
class ProductRepositoryTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var productRepo: ProductRepository
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        productRepo = ProductRepository(ProductLocalDataSource(db.productDao()))
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetProducts() = runBlocking {
        assertThat(productRepo.getAll(), iz(emptyList()))
        assertNull(productRepo.getById(productEntity.productId))

        productRepo.insert(productEntity)
        val result = productRepo.getAll()

        assertThat(result, iz(not(empty())))
        assertThat(result, iz(listOf(productEntity)))
        assertThat(productRepo.getById(productEntity.productId), iz(productEntity))
    }
}
