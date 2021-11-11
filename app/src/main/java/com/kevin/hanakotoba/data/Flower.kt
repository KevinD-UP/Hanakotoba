package com.kevin.hanakotoba.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "flower")
data class Flower(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)