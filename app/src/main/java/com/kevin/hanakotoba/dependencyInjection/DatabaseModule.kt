package com.kevin.hanakotoba.dependencyInjection

import android.content.Context
import com.kevin.hanakotoba.data.AppDatabase
import com.kevin.hanakotoba.data.FlowerDao
import com.kevin.hanakotoba.data.GardenDao
import javax.inject.Singleton
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providePlantDao(appDatabase: AppDatabase): FlowerDao {
        return appDatabase.flowerDao()
    }

    @Provides
    fun provideGardenPlantingDao(appDatabase: AppDatabase): GardenDao {
        return appDatabase.gardenFloweringDao()
    }
}