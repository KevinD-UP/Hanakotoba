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
    val wateringInterval: Int = 7,
    val imageUrl: String = ""
) : Serializable {


    override fun toString() = name
}

