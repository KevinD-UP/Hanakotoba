package com.kevin.hanakotoba.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface PlantDao {
    @Query("SELECT * FROM flower ORDER BY name")
    fun getPlants(): Flow<List<Flower>>

    @Query("SELECT * FROM flower WHERE id = :plantId")
    fun getPlant(plantId: String): Flow<Flower>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<Flower>)
}