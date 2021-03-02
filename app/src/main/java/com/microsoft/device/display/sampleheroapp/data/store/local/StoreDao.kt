/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.store.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.microsoft.device.display.sampleheroapp.data.store.model.CityEntity
import com.microsoft.device.display.sampleheroapp.data.store.model.CityWithStoresEntity
import com.microsoft.device.display.sampleheroapp.data.store.model.StoreEntity

@Dao
interface StoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(store: StoreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg store: StoreEntity)

    @Query("SELECT * FROM stores")
    suspend fun getAll(): List<StoreEntity>

    @Query("SELECT * FROM stores where storeId = :storeId")
    suspend fun getById(storeId: Long): StoreEntity?

    @Transaction
    @Query("SELECT * FROM cities")
    suspend fun getCityWithStores(): List<CityWithStoresEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(vararg city: CityEntity)
}
