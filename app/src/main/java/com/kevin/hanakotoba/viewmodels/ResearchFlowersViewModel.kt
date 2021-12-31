package com.kevin.hanakotoba.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kevin.hanakotoba.data.Flower
import com.kevin.hanakotoba.data.FlowerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResearchFlowersViewModel  @Inject internal constructor(
    flowerRepository: FlowerRepository,
) : ViewModel() {

    val flowers: LiveData<List<Flower>> = flowerRepository.getFlowers().asLiveData()

}

