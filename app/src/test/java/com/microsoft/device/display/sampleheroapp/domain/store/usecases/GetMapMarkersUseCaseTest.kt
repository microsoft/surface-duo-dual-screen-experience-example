/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.store.usecases

import com.microsoft.device.display.sampleheroapp.domain.store.testutil.MockStoreDataSource
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.cityEntity
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.cityMarkerModel
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.hiddenCityEntity
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.storeEntity
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.storeMarkerModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class GetMapMarkersUseCaseTest {

    private lateinit var getMapMarkersUseCase: GetMapMarkersUseCase
    private lateinit var mockRepo: MockStoreDataSource

    @Before
    fun setup() {
        mockRepo = MockStoreDataSource()
        getMapMarkersUseCase = GetMapMarkersUseCase(mockRepo)
    }

    @Test
    fun getAllWhenRepoHasNoData() = runBlocking {
        assertThat(emptyList(), iz(getMapMarkersUseCase.getAll()))
    }

    @Test
    fun getAllWhenRepoHasNoCities() = runBlocking {
        mockRepo.insert(storeEntity)
        assertThat(listOf(storeMarkerModel), iz(getMapMarkersUseCase.getAll()))
    }

    @Test
    fun getAllWhenRepoHasNoStores() = runBlocking {
        mockRepo.insertCities(cityEntity, hiddenCityEntity)
        assertThat(emptyList(), iz(getMapMarkersUseCase.getAll()))
    }

    @Test
    fun getAllWhenRepoHasACityAndAStore() = runBlocking {
        mockRepo.insert(storeEntity)
        mockRepo.insertCities(cityEntity)
        assertThat(listOf(cityMarkerModel, storeMarkerModel), iz(getMapMarkersUseCase.getAll()))
    }

    @Test
    fun getAllWhenRepoHasMultipleCitiesAndAStore() = runBlocking {
        mockRepo.insert(storeEntity)
        mockRepo.insertCities(cityEntity, hiddenCityEntity)
        assertThat(listOf(cityMarkerModel, storeMarkerModel), iz(getMapMarkersUseCase.getAll()))
    }
}
