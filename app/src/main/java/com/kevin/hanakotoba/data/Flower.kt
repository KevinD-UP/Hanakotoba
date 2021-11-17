package com.kevin.hanakotoba.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "flower")
data class Flower(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val flower_id: Int,
    val name: String,
    val latinName: String,
    val description: String,
    val wateringInterval: Int = 7, //How often the plant should be watering in day.
    val imageUrl: String = ""
){
    /**
     * Determines if the plant should be watered.  Returns true if [since]'s date > date of last
     * watering + watering Interval; false otherwise.
     */
    fun shouldBeWatered(since: Calendar, lastWateringDate: Calendar) =
        since > lastWateringDate.apply { add(Calendar.DAY_OF_YEAR, wateringInterval) }

    override fun toString() = name
}

