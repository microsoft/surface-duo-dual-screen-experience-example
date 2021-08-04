/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.di

import com.microsoft.device.samples.dualscreenexperience.data.about.LicenseDataSource
import com.microsoft.device.samples.dualscreenexperience.data.about.LicenseRepository
import com.microsoft.device.samples.dualscreenexperience.data.catalog.CatalogDataSource
import com.microsoft.device.samples.dualscreenexperience.data.catalog.CatalogRepository
import com.microsoft.device.samples.dualscreenexperience.data.order.OrderDataSource
import com.microsoft.device.samples.dualscreenexperience.data.order.OrderRepository
import com.microsoft.device.samples.dualscreenexperience.data.product.ProductDataSource
import com.microsoft.device.samples.dualscreenexperience.data.product.ProductRepository
import com.microsoft.device.samples.dualscreenexperience.data.store.StoreDataSource
import com.microsoft.device.samples.dualscreenexperience.data.store.StoreRepository
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
    abstract fun provideStoreRepo(repository: StoreRepository): StoreDataSource

    @Singleton
    @Binds
    abstract fun provideCatalogRepository(provider: CatalogRepository): CatalogDataSource

    @Singleton
    @Binds
    abstract fun provideProductRepo(repository: ProductRepository): ProductDataSource

    @Singleton
    @Binds
    abstract fun provideOrderRepo(repository: OrderRepository): OrderDataSource

    @Singleton
    @Binds
    abstract fun provideLicenseRepository(provider: LicenseRepository): LicenseDataSource
}
