/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.store.usecases

import com.microsoft.device.display.sampleheroapp.domain.store.testutil.MockStoreDataSource
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.cityEntity
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.hiddenCityEntity
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.store
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.storeEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class GetStoresByCityUseCaseTest {

    private lateinit var getStoresByCityUseCase: GetStoresByCityUseCase
    private lateinit var mockRepo: MockStoreDataSource

    @Before
    fun setup() {
        mockRepo = MockStoreDataSource()
        getStoresByCityUseCase = GetStoresByCityUseCase(mockRepo)
    }

    @Test
    fun testGetAll_whenRepoHasNoData() = runBlocking {
        assertThat(emptyList(), iz(getStoresByCityUseCase.getAll(cityEntity.name)))
    }

    @Test
    fun testGetAll_whenRepoHasNoStores() = runBlocking {
        mockRepo.insertCity(cityEntity)
        assertThat(emptyList(), iz(getStoresByCityUseCase.getAll(cityEntity.name)))
    }

    @Test
    fun testGetAll_whenRepoHasNoCities() = runBlocking {
        mockRepo.insert(storeEntity)
        assertThat(emptyList(), iz(getStoresByCityUseCase.getAll(cityEntity.name)))
    }

    @Test
    fun testGetAll_whenRepoHasStoresAndCitiesUnlinked() = runBlocking {
        mockRepo.insert(storeEntity)
        mockRepo.insertCity(hiddenCityEntity)
        assertThat(emptyList(), iz(getStoresByCityUseCase.getAll(cityEntity.name)))
    }

    @Test
    fun testGetAll_whenRepoHasStoresAndCitiesLinked() = runBlocking {
        mockRepo.insert(storeEntity)
        mockRepo.insertCity(cityEntity, hiddenCityEntity)
        assertThat(listOf(store), iz(getStoresByCityUseCase.getAll(cityEntity.name)))
    }
}
