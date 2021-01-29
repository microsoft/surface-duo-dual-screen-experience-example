/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.di

import android.content.Context
import androidx.room.Room
import com.microsoft.device.display.sampleheroapp.data.product.local.ProductDao
import com.microsoft.device.display.sampleheroapp.data.product.local.ProductDatabase
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
    fun provideDatabase(@ApplicationContext appContext: Context): ProductDatabase =
        Room.databaseBuilder(
            appContext,
            ProductDatabase::class.java,
            "products_db"
        )
            .createFromAsset("database/products_db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideProductDao(database: ProductDatabase): ProductDao = database.productDao()
}
