package com.kevin.hanakotoba.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GardenRepository @Inject constructor(
    private val gardenPlantingDao: GardenDao
) {

    fun isPlanted(plantId: Int) =
        gardenPlantingDao.isPlanted(plantId)

    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()

    fun insertFlowerInGarden(flowerId: Int) {
        val garden = Garden(flowerId)
        gardenPlantingDao.insertFlowerInGarden(garden)
    }

    fun deleteFlowerInGarden(flowerId: Int) {
        val garden = Garden(flowerId)
        gardenPlantingDao.deleteFlowerInGarden(garden)
    }
}
