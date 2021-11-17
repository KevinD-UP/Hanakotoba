package com.kevin.hanakotoba.data;

import androidx.room.Embedded
import androidx.room.Relation

/**
 * This class captures the relationship between a [Flower] and a user's [Garden], which is
 * used by Room to fetch the related entities.
 */
data class FlowerAndGarden(
        @Embedded
        val plant: Flower,

        @Relation(parentColumn = "id", entityColumn = "flower_id")
        val gardenPlantings: List<Garden> = emptyList()
)
