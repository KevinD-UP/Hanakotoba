package com.kevin.hanakotoba.data

import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GardenRepository @Inject constructor(
    private val gardenPlantingDao: GardenDao
) {

    fun isPlanted(plantId: Int) =
        gardenPlantingDao.isPlanted(plantId)

    fun getPlantedGardens() = gardenPlantingDao.getPlantedGardens()

    suspend fun insertFlowerInGarden(flowerId: Int, lastWateringDate : Calendar) {
        val garden = Garden(flowerId)
        garden.lastWateringDate = lastWateringDate
        gardenPlantingDao.insertFlowerInGarden(garden)
    }

    suspend fun deleteFlowerInGarden(flowerId: Int) {
        gardenPlantingDao.deleteFlowerInGarden(flowerId)
    }

    suspend fun updateFlowerInGarden(flowerId: Int){
        val garden = Garden(flowerId)
        gardenPlantingDao.updateFlowerInGarden(garden)
    }
}
