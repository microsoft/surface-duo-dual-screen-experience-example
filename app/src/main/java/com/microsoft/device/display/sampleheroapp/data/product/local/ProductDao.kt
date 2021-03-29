/*
 *
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 *
 */

package com.microsoft.device.display.sampleheroapp.data.product.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.microsoft.device.display.sampleheroapp.data.product.model.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = REPLACE)
    suspend fun save(product: ProductEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insert(vararg products: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity)

    @Query("SELECT * FROM products where productId = :productId")
    suspend fun getById(productId: Long): ProductEntity?

    @Query("SELECT * FROM products")
    suspend fun getAll(): List<ProductEntity>
}
