package com.kevin.hanakotoba.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.data.FlowerRepository
import com.kevin.hanakotoba.data.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlowerDescriptionViewModel @Inject internal constructor(
    private val gardenRepository: GardenRepository
) : ViewModel() {

        fun addFlowerInGarden(flowerId: Int): Unit =
         gardenRepository.insertFlowerInGarden(flowerId)

        fun deleteFlowerInGarden(flowerId: Int): Unit =
         gardenRepository.deleteFlowerInGarden(flowerId)

        fun updateFlowerInGarden(flowerId: Int): Unit =
            gardenRepository.updateFlowerInGarden(flowerId)
}