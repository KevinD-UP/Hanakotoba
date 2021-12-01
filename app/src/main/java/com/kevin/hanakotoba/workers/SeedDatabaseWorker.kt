package com.kevin.hanakotoba.workers

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.kevin.hanakotoba.data.AppDatabase
import com.kevin.hanakotoba.data.Flower
import java.util.*


class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val flowerType = object : TypeToken<List<Flower>>() {}.type
                        val flowerList: List<Flower> = Gson().fromJson(jsonReader, flowerType)
                        flowerList.map { flower -> flower.lastWateringDate = Calendar.getInstance() }
                        val database = AppDatabase.getInstance(applicationContext)
                        database.flowerDao().insertAll(flowerList)

                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error seeding database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
        const val KEY_FILENAME = "FLOWER_DATA_FILENAME"
    }
}