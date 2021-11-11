package com.kevin.hanakotoba.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FlowerDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFlower(flower : Flower)

    @Query("SELECT * FROM flower ORDER BY id ASC")
    fun getFlowers() : LiveData<List<Flower>>

    @Query("DELETE FROM flower")
    fun deleteAllData()



}