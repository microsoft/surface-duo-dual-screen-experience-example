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
import com.microsoft.device.display.sampleheroapp.data.product.local.model.Product

@Dao
interface ProductDao {
    @Insert(onConflict = REPLACE)
    suspend fun save(product: Product)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg products: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT * FROM products where id = :productId")
    suspend fun load(productId: String): Product?

    @Query("SELECT * from products WHERE name LIKE :searchKey")
    suspend fun findByName(searchKey: String): List<Product>?

    @Query("SELECT * FROM products")
    suspend fun getAll(): List<Product>?
}
