package com.kevin.hanakotoba.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*


@Entity(tableName = "flower")
data class Flower(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val flower_id: Int,
    val name: String,
    val latinName: String,
    val description: String,
    var lastWateringDate: Calendar = Calendar.getInstance(),
    val wateringInterval: Int = 7, //How often the plant should be watering in day.
    val imageUrl: String = ""
) : Serializable {

    /**
     * Determines if the plant should be watered.
     */
    fun shouldBeWatered(): Boolean {
        val limit = this.lastWateringDate
        limit.add(Calendar.DATE, wateringInterval)
        return limit >= Calendar.getInstance()
    }

    fun watered() {
        lastWateringDate = Calendar.getInstance()
    }


    override fun toString() = name
}

