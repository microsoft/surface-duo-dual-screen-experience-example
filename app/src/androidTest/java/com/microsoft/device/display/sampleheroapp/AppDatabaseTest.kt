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
import com.microsoft.device.display.sampleheroapp.data.AppDatabase
import com.microsoft.device.display.sampleheroapp.data.product.local.ProductDao
import com.microsoft.device.display.sampleheroapp.data.product.model.ProductEntity
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
class AppDatabaseTest {

    private lateinit var productDao: ProductDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        productDao = db.productDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetProducts() = runBlocking {
        assertThat(productDao.getAll(), iz(emptyList()))
        assertNull(productDao.load(5))

        val product = ProductEntity("guitar", 30, "Great", 4f)
        product.id = 3
        productDao.insertAll(product)
        val result = productDao.getAll()
        assertThat(result, iz(not(empty())))
        assertThat(result[0], iz(product))
        assertThat(productDao.load(3), iz(product))
    }
}
