package com.kevin.hanakotoba.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GardenDao {
    @Query("SELECT * FROM garden")
    fun getGardens(): Flow<List<Garden>>

    @Query("SELECT EXISTS(SELECT 1 FROM garden WHERE flower_id = :plantId LIMIT 1)")
    fun isPlanted(plantId: Int): Flow<Boolean>

    @Transaction
    @Query("SELECT  *  FROM flower, garden where flower.id = garden.flower_id")
    fun getPlantedGardens(): Flow<List<FlowerAndGarden>>

    @Transaction
    @Query("SELECT  *  FROM flower, garden where flower.id = garden.flower_id and garden_flower_id = :id")
    suspend fun getFlowerFromGarden(id : Int): FlowerAndGarden

    @Insert
    suspend fun insertFlowerInGarden(garden: Garden): Long

    @Query("DELETE FROM garden WHERE garden_flower_id = :flowerId")
    suspend fun deleteFlowerInGarden(flowerId: Long)

    @Update
    suspend fun updateFlowerInGarden(garden: Garden)

}
