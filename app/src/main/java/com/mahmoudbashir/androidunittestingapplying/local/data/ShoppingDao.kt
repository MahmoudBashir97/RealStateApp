package com.mahmoudbashir.androidunittestingapplying.local.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(item: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_item")
    fun observeAllShoppingItems():LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price * amount) FROM shopping_item")
    fun obsereveTotalPrice():LiveData<Float>


}