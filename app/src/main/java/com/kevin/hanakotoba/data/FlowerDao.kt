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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(flowers: Flower)

    @Update
    suspend fun updateFlower(flower: Flower)

    @Query("UPDATE flower SET lastWateringDate=datetime('now') WHERE id = :flowerId")
    suspend fun wateredFlower(flowerId: Int)

    @Delete
    suspend fun deleteFlower(flower: Flower)
}