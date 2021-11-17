package com.kevin.hanakotoba.data

import androidx.room.*
import java.util.*

/**
 * [Garden] represents when a user adds a [Flower] to their garden, with useful metadata.
 * Properties such as [lastWateringDate] are used for notifications (such as when to water the
 * plant).
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
@Entity(
    tableName = "garden",
    foreignKeys = [
        ForeignKey(entity = Flower::class, parentColumns = ["id"], childColumns = ["flower_id"])
    ],
    indices = [Index("flower_id")]
)
data class Garden(
    @ColumnInfo(name = "flower_id") val flowerId: String,

    /**
     * Indicates when the [Flower] was planted. Used for showing notification when it's time
     * to harvest the plant.
     */
    @ColumnInfo(name = "flower_date") val flowerDate: Calendar = Calendar.getInstance(),

    /**
     * Indicates when the [Flower] was last watered. Used for showing notification when it's
     * time to water the plant.
     */
    @ColumnInfo(name = "last_watering_date")
    val lastWateringDate: Calendar = Calendar.getInstance()
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var garden_Id: Long = 0
}