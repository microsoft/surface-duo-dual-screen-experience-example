/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.store

import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.device.display.sampleheroapp.data.AppDatabase
import com.microsoft.device.display.sampleheroapp.data.store.local.StoreLocalDataSource
import com.microsoft.device.display.sampleheroapp.data.store.model.CityWithStoresEntity
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.core.Is.`is` as iz

@RunWith(AndroidJUnit4ClassRunner::class)
class StoreRepositoryTest {

    private val cityWithStoreEntityList =
        listOf(CityWithStoresEntity(cityEntityRedmond, mutableListOf(storeEntityJoy)))

    private lateinit var database: AppDatabase
    private lateinit var storesRepo: StoreRepository

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        storesRepo = StoreRepository(StoreLocalDataSource(database.storeDao()))
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertAndGetStore() = runBlocking {
        assertThat(storesRepo.getAll(), iz(emptyList()))
        assertNull(storesRepo.getById(storeEntityJoy.storeId))

        storesRepo.insert(storeEntityJoy)
        val result = storesRepo.getAll()

        assertThat(result, iz(not(Matchers.empty())))
        assertThat(result, iz(listOf(storeEntityJoy)))
        assertThat(storesRepo.getById(storeEntityJoy.storeId), iz(storeEntityJoy))
    }

    @Test
    fun insertCityAndGetStores() = runBlocking {
        assertThat(storesRepo.getCitiesWithStores(), iz(emptyList()))

        storesRepo.insert(storeEntityJoy)
        storesRepo.insertCity(cityEntityRedmond)
        val result = storesRepo.getCitiesWithStores()

        assertThat(result, iz(not(Matchers.empty())))
        assertThat(result, iz(cityWithStoreEntityList))
    }
}
