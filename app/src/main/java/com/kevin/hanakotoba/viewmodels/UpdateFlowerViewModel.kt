package com.kevin.hanakotoba.viewmodels

import androidx.lifecycle.ViewModel
import com.kevin.hanakotoba.data.FlowerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateFlowerViewModel @Inject internal constructor(
    private val flowerRepository: FlowerRepository
): ViewModel() {
    // TODO: Implement the ViewModel
}