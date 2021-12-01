package com.kevin.hanakotoba.data

import androidx.room.*
import java.util.*

/**
 * [Garden] represents when a user adds a [Flower] to their garden.
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
    @ColumnInfo(name = "flower_id") val flowerId: Int,

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var gardenId: Long = 0
}