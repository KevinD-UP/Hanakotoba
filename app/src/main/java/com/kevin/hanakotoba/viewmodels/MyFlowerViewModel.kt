package com.kevin.hanakotoba.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kevin.hanakotoba.data.FlowerAndGarden
import com.kevin.hanakotoba.data.GardenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyFlowerViewModel @Inject internal constructor(
    gardenRepository: GardenRepository
) : ViewModel() {
    val flowerAndGarden: LiveData<List<FlowerAndGarden>> =
        gardenRepository.getPlantedGardens().asLiveData()


}
