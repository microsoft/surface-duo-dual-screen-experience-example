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
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.secondProduct
import com.microsoft.device.display.sampleheroapp.domain.product.testutil.secondProductEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.hamcrest.core.Is.`is` as iz

class GetProductsUseCaseTest {
    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var mockRepo: MockProductDataSource

    @Before
    fun setup() {
        mockRepo = MockProductDataSource()
        getProductsUseCase = GetProductsUseCase(mockRepo)
    }

    @Test
    fun getEmptyListWhenRepoHasNoData() = runBlocking {
        assertThat(emptyList(), iz(getProductsUseCase.getAll()))
    }

    @Test
    fun getAllWhenRepoHasAProduct() = runBlocking {
        mockRepo.insert(productEntity)
        assertThat(listOf(product), iz(getProductsUseCase.getAll()))
    }

    @Test
    fun getAllWhenRepoHasMultipleProducts() = runBlocking {
        mockRepo.insert(productEntity)
        mockRepo.insert(secondProductEntity)
        assertThat(listOf(product, secondProduct), iz(getProductsUseCase.getAll()))
    }
}
