package com.kevin.hanakotoba.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FlowerViewModel(application :Application) : AndroidViewModel(application){

    private val flowerDAO = FlowerDB.getDatabase(application).flowerDao()
    private val mAllFlowers : LiveData<List<Flower>> = flowerDAO.getFlowers()

    fun addFlower(flower : Flower){
        viewModelScope.launch (Dispatchers.IO){
            flowerDAO.addFlower(flower)
        }
    }

    fun getAllFlowers(): LiveData<List<Flower>> {
        return mAllFlowers
    }

    fun deleteAllData(){
        viewModelScope.launch (Dispatchers.IO){
            flowerDAO.deleteAllData()
        }
    }

}