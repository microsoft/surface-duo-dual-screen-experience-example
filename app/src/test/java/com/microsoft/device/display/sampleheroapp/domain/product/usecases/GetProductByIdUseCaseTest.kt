/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.domain.product.usecases

import com.microsoft.device.display.sampleheroapp.domain.product.testutil.MockProductDataSource
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.product
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.productEntity
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class GetProductByIdUseCaseTest {

    private lateinit var getProductByIdUseCase: GetProductByIdUseCase
    private lateinit var mockRepo: MockProductDataSource

    @Before
    fun setup() {
        mockRepo = MockProductDataSource()
        getProductByIdUseCase = GetProductByIdUseCase(mockRepo)
    }

    @Test
    fun getNullWhenIdDoesNotExist() = runBlocking {
        assertNull(getProductByIdUseCase.getById(-1))
    }

    @Test
    fun getProductWhenIdExists() = runBlocking {
        mockRepo.insert(productEntity)
        assertThat(product, iz(getProductByIdUseCase.getById(productEntity.productId)))
    }
}
