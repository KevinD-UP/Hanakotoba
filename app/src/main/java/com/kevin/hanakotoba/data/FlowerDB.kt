package com.kevin.hanakotoba.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities =[Flower::class],version =1, exportSchema = true)
abstract class FlowerDB : RoomDatabase() {

    abstract fun flowerDao() : FlowerDAO

    companion object{
        @Volatile
        private var INSTANCE : FlowerDB? = null

        fun getDatabase(context: Context):FlowerDB{
            if (INSTANCE != null)
                return INSTANCE!!
            val db = Room.databaseBuilder(
                context.applicationContext,
                FlowerDB::class.java, "Hanalala"
            ).createFromAsset("database/Hana.db").build()
            INSTANCE = db
            return INSTANCE!!
        }
    }
}