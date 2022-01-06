package com.kevin.hanakotoba.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.data.FlowerRepository
import com.kevin.hanakotoba.data.Garden
import com.kevin.hanakotoba.data.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FlowerDescriptionViewModel @Inject internal constructor(
    private val gardenRepository: GardenRepository,
    private val flowerRepository: FlowerRepository
) : ViewModel() {

        fun addFlowerInGarden(flower: Flower, lastWateringDate : Calendar) : MutableLiveData<Long> {
            val result = MutableLiveData<Long>()
            viewModelScope.launch {
                val data  = gardenRepository.insertFlowerInGarden(flower,lastWateringDate)
                result.value = data
            }
            return result
        }

        fun deleteFlowerInGarden(flowerId: Long) {
            viewModelScope.launch {
                gardenRepository.deleteFlowerInGarden(flowerId)
            }
        }
        fun updateFlowerInGarden(gardenFlower : Garden) {
            viewModelScope.launch {
                gardenRepository.updateFlowerInGarden(gardenFlower)
            }
        }

        fun deleteFlower(flower: Flower){
            viewModelScope.launch {
                flowerRepository.deleteFlower(flower)
            }
        }

        fun isPlanted(flowerId: Int): Flow<Boolean> {
            return gardenRepository.isPlanted(flowerId)
        }
}