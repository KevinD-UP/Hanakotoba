package com.kevin.hanakotoba.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Flower class.
 */
@Dao
interface FlowerDao {
    @Query("SELECT * FROM flower ORDER BY name")
    fun getFlowers(): Flow<List<Flower>>

    @Query("SELECT * FROM flower WHERE id = :flowerId")
    fun getFlower(flowerId: String): Flow<Flower>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(flowers: List<Flower>)
}