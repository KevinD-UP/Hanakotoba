package com.kevin.hanakotoba.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the [Garden] class.
 */
@Dao
interface GardenDao {
    @Query("SELECT * FROM garden")
    fun getGardens(): Flow<List<Garden>>

    @Query("SELECT EXISTS(SELECT 1 FROM garden WHERE flower_id = :plantId LIMIT 1)")
    fun isPlanted(plantId: Int): Flow<Boolean>

    /**
     * This query will tell Room to query both the [Flower] and [Garden] tables and handle
     * the object mapping.
     */
    @Transaction
/*    @Query("SELECT  *  FROM flower, garden where flower.id = garden.flower_id")*/
/*    @Query("SELECT * FROM flower WHERE id IN (SELECT DISTINCT(flower_id) FROM garden)")*/
    @Query("SELECT  *  FROM flower, garden where flower.id = garden.flower_id")
    fun getPlantedGardens(): Flow<List<FlowerAndGarden>>

    @Insert
    suspend fun insertFlowerInGarden(garden: Garden): Long

    @Query("DELETE FROM garden WHERE flower_id = :flowerId")
    suspend fun deleteFlowerInGarden(flowerId: Int)

    @Update
    suspend fun updateFlowerInGarden(garden: Garden)

}
