/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.di

import com.microsoft.device.display.sampleheroapp.data.order.OrderDataSource
import com.microsoft.device.display.sampleheroapp.data.order.OrderRepository
import com.microsoft.device.display.sampleheroapp.data.product.ProductDataSource
import com.microsoft.device.display.sampleheroapp.data.product.ProductRepository
import com.microsoft.device.display.sampleheroapp.data.store.StoreDataSource
import com.microsoft.device.display.sampleheroapp.data.store.StoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {

    @Singleton
    @Binds
    abstract fun provideProductRepo(repository: ProductRepository): ProductDataSource

    @Singleton
    @Binds
    abstract fun provideStoreRepo(repository: StoreRepository): StoreDataSource

    @Singleton
    @Binds
    abstract fun provideOrderRepo(repository: OrderRepository): OrderDataSource
}
