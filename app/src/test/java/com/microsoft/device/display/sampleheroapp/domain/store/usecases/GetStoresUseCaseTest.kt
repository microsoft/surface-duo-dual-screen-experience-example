/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.store.usecases

import com.microsoft.device.display.sampleheroapp.domain.store.testutil.MockStoreDataSource
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.store
import com.microsoft.device.display.sampleheroapp.domain.store.testutil.storeEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class GetStoresUseCaseTest {

    private lateinit var getStoresUseCase: GetStoresUseCase
    private lateinit var mockRepo: MockStoreDataSource

    @Before
    fun setup() {
        mockRepo = MockStoreDataSource()
        getStoresUseCase = GetStoresUseCase(mockRepo)
    }

    @Test
    fun getAllWhenRepoHasNoStores() = runBlocking {
        assertThat(emptyList(), iz(getStoresUseCase.getAll()))
    }

    @Test
    fun getAllWhenRepoHasAStore() = runBlocking {
        mockRepo.insert(storeEntity)
        assertThat(listOf(store), iz(getStoresUseCase.getAll()))
    }

    @Test
    fun getByIdWhenRepoHasNoStores() = runBlocking {
        assertThat(null, iz(getStoresUseCase.getById(storeEntity.storeId)))
    }

    @Test
    fun getByIdWhenRepoHasStoreId() = runBlocking {
        mockRepo.insert(storeEntity)
        assertThat(listOf(store), iz(getStoresUseCase.getAll()))
    }
}
