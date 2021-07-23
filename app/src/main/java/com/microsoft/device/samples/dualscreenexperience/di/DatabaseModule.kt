/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.samples.dualscreenexperience.di

import android.content.Context
import androidx.room.Room
import com.microsoft.device.samples.dualscreenexperience.config.LocalStorageConfig.DB_ASSET_PATH
import com.microsoft.device.samples.dualscreenexperience.config.LocalStorageConfig.DB_NAME
import com.microsoft.device.samples.dualscreenexperience.data.AppDatabase
import com.microsoft.device.samples.dualscreenexperience.data.order.local.OrderDao
import com.microsoft.device.samples.dualscreenexperience.data.product.local.ProductDao
import com.microsoft.device.samples.dualscreenexperience.data.store.local.StoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room
            .databaseBuilder(
                appContext,
                AppDatabase::class.java,
                DB_NAME
            )
            .createFromAsset(DB_ASSET_PATH)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao = database.productDao()

    @Singleton
    @Provides
    fun provideStoreDao(database: AppDatabase): StoreDao = database.storeDao()

    @Singleton
    @Provides
    fun provideOrderDao(database: AppDatabase): OrderDao = database.orderDao()
}
