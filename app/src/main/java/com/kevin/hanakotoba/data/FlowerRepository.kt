package com.kevin.hanakotoba.data

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [FlowerDao] is main-safe.  Room supports Coroutines and moves the
 * query execution off of the main thread.
 */
@Singleton
class FlowerRepository @Inject constructor(private val flowerDao: FlowerDao) {

    fun getFlowers() = flowerDao.getFlowers()

    fun getFlower(flowerId: Int) = flowerDao.getFlower(flowerId)

    suspend fun updateFlower(flower: Flower) = flowerDao.updateFlower(flower)

    suspend fun deleteFlower(flower: Flower) = flowerDao.deleteFlower(flower)

}
