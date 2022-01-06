package com.kevin.hanakotoba.data

import android.util.Log
import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable
import java.util.*

data class FlowerAndGarden(
        @Embedded
        val garden : Garden,

        @Relation(parentColumn = "flower_id", entityColumn = "id")
        val flower: Flower
): Serializable {

    fun canBeWatered(): Boolean {
        Log.d("DEBUG","CHECK CAN BE WATERED ")

        val nextWateringDate = this.garden.nextWateringDate
        val currentDate = Calendar.getInstance()

        Log.d("DEBUG",nextWateringDate.get(Calendar.DAY_OF_YEAR).toString())
        Log.d("DEBUG",currentDate.get(Calendar.DAY_OF_YEAR).toString())


        return nextWateringDate.get(Calendar.DAY_OF_YEAR) == currentDate.get(Calendar.DAY_OF_YEAR) &&
                nextWateringDate.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH) &&
                nextWateringDate.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR)

    }
}


