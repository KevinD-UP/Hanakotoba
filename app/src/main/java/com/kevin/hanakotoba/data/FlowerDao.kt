package com.kevin.hanakotoba.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Flower class.
 */
@Dao
interface FlowerDao {
    @Query("SELECT * FROM flower ORDER BY name")
    fun getFlowers(): Flow<List<Flower>>

    @Query("SELECT * FROM flower WHERE id = :flowerId")
    fun getFlower(flowerId: Int): Flow<Flower>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(flowers: List<Flower>)

    @Update
    suspend fun updateFlower(flower: Flower)

    @Delete
    suspend fun deleteFlower(flower: Flower)
}