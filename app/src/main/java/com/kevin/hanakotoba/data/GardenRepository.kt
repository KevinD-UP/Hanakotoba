package com.kevin.hanakotoba.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GardenPlantingRepository @Inject constructor(
    private val gardenPlantingDao: GardenDao
) {

    suspend fun createGardenPlanting(plantId: String) {
        val gardenPlanting = Garden(plantId)
        gardenPlantingDao.insertGarden(gardenPlanting)
    }

    suspend fun removeGarden(gardenPlanting: Garden) {
        gardenPlantingDao.deleteGarden(gardenPlanting)
    }

    fun isPlanted(plantId: String) =
        gardenPlantingDao.isPlanted(plantId)

    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()
}
