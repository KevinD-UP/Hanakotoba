package com.kevin.hanakotoba.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.data.FlowerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateFlowerViewModel @Inject internal constructor(
    private val flowerRepository: FlowerRepository
): ViewModel() {

    fun updateFlower(flower: Flower) {
        viewModelScope.launch {
            flowerRepository.updateFlower(flower)
        }
    }
}