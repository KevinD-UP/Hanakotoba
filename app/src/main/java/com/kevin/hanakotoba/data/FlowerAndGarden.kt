package com.kevin.hanakotoba.data;

import androidx.room.Embedded
import androidx.room.Relation
import java.io.Serializable

data class FlowerAndGarden(
        @Embedded
        val garden : Garden,

        @Relation(parentColumn = "flower_id", entityColumn = "id")
        val flower: Flower
): Serializable {

        /**
         * Determines if the plant should be watered.
         */
        /*   fun shouldBeWatered(): Boolean {
               val limit = this.lastWateringDate
               limit.add(Calendar.DATE, wateringInterval)
               return limit >= Calendar.getInstance()
           }

           fun watered() {
               lastWateringDate = Calendar.getInstance()
           }*/

}


