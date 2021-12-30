package com.kevin.hanakotoba.data

import android.app.AlertDialog
import android.util.Log
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

    suspend fun getFlowerFromGarden(id :  Int ) = gardenPlantingDao.getFlowerFromGarden(id)

    suspend fun insertFlowerInGarden(flower: Flower, lastWateringDate : Calendar) : Long {
        val garden = Garden(flower.flower_id)
        garden.lastWateringDate = lastWateringDate

        val year = lastWateringDate.get(Calendar.YEAR)
        val month = lastWateringDate.get(Calendar.MONTH)
        val day = lastWateringDate.get(Calendar.DAY_OF_MONTH)
        Log.d("INSERT","$day $month $year")

        val nextWateringDate = lastWateringDate.clone() as Calendar
        nextWateringDate.add(Calendar.DATE,flower.wateringInterval)
        garden.nextWateringDate = nextWateringDate

        return gardenPlantingDao.insertFlowerInGarden(garden)
    }

    suspend fun deleteFlowerInGarden(flowerId: Long) {
        gardenPlantingDao.deleteFlowerInGarden(flowerId)
    }

    suspend fun updateFlowerInGarden(gardenFlower : Garden){
        gardenPlantingDao.updateFlowerInGarden(gardenFlower)
    }
}
