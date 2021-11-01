/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.domain.catalog.usecases

import com.microsoft.device.samples.dualscreenexperience.data.catalog.CatalogDataSource
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class GetCatalogListUseCaseTest {
    private lateinit var getCatalogListUseCase: GetCatalogListUseCase
    private lateinit var mockRepo: CatalogDataSource

    @Before
    fun setup() {
        mockRepo = MockCatalogDataSource()
        getCatalogListUseCase = GetCatalogListUseCase(mockRepo)
    }

    @Test
    fun getSimpleList() = runBlocking {
        assertThat(listOf(catalogItem), iz(getCatalogListUseCase.getAll()))
    }
}
