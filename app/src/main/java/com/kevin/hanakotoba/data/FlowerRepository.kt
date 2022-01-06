package com.kevin.hanakotoba.data

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations on [Flower] via [FlowerDao].
 */
@Singleton
class FlowerRepository @Inject constructor(private val flowerDao: FlowerDao) {

    fun getFlowers() = flowerDao.getFlowers()

    suspend fun insertFlower(flower: Flower) = flowerDao.insert(flower)

    suspend fun updateFlower(flower: Flower) = flowerDao.updateFlower(flower)

    suspend fun deleteFlower(flower: Flower) = flowerDao.deleteFlower(flower)

}
