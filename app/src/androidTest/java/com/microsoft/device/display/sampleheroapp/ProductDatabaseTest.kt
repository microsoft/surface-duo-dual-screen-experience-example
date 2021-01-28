/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.device.display.sampleheroapp.data.product.local.ProductDao
import com.microsoft.device.display.sampleheroapp.data.product.local.ProductDatabase
import com.microsoft.device.display.sampleheroapp.data.product.model.ProductEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ProductDatabaseTest {

    private lateinit var productDao: ProductDao
    private lateinit var db: ProductDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, ProductDatabase::class.java).build()
        productDao = db.productDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetProducts() = runBlocking {
        val product = ProductEntity("chair", 30, "Great")
        productDao.insertAll(product)
        val result = productDao.getAll()
        assertEquals(result!![0], product)
    }
}
