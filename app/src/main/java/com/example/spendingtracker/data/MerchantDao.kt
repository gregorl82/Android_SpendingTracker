package com.example.spendingtracker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.spendingtracker.data.entities.Merchant

@Dao
interface MerchantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMerchant(merchant: Merchant)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMerchants(vararg merchant: Merchant)

    @Update
    suspend fun updateMerchant(merchant: Merchant)

    @Delete
    suspend fun deleteMerchant(merchant: Merchant)

    @Query("DELETE FROM merchants")
    suspend fun deleteAllMerchants()

    @Query("SELECT * FROM merchants")
    fun getAllMerchants(): LiveData<List<Merchant>>

    @Query("SELECT * FROM merchants WHERE id = :id")
    fun getMerchantById(id: String): LiveData<Merchant>
}