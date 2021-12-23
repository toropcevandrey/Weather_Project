package com.example.weather_project.di.modules

import android.content.Context
import androidx.room.Room
import com.example.weather_project.data.api.ApiService
import com.example.weather_project.data.db.WeatherDataDao
import com.example.weather_project.data.db.WeatherDataRoomDatabase
import com.example.weather_project.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule(private val context: Context) {

    @Provides
    @Singleton
    fun providesMainRepository(apiService: ApiService, weatherDataDao: WeatherDataDao) =
        MainRepository(apiService, weatherDataDao)

    @Provides
    @Singleton
    fun provideRoomDatabase() =
        Room.databaseBuilder(
            context,
            WeatherDataRoomDatabase::class.java, "weather_table"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideDao(db: WeatherDataRoomDatabase) = db.weatherDataDao()
}