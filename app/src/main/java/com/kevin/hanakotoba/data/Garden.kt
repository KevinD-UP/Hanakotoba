package com.kevin.hanakotoba.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

/**
 * [Garden] represents when a user adds a [Flower] to their garden.
 */
@Entity(
    tableName = "garden",
    foreignKeys = [
        ForeignKey(entity = Flower::class, parentColumns = ["id"], childColumns = ["flower_id"],onDelete =CASCADE)
    ]
)
data class Garden(
    @ColumnInfo(name = "flower_id") val flowerId: Int,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "garden_flower_id")
    var gardenId: Long = 0
    @ColumnInfo(name = "last_watering_date")
    var lastWateringDate : Calendar = Calendar.getInstance()

    @ColumnInfo(name = "next_watering_date")
    var nextWateringDate : Calendar = Calendar.getInstance()



}