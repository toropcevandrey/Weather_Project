package com.example.weather_project.di.modules

import android.content.Context
import androidx.room.Room
import com.example.weather_project.data.api.ApiService
import com.example.weather_project.data.db.WeatherDBDao
import com.example.weather_project.data.db.WeatherDBRoomDatabase
import com.example.weather_project.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule(val context: Context) {

    @Provides
    @Singleton
    fun providesMainRepository(apiService: ApiService, weatherDBDao: WeatherDBDao) =
        MainRepository(apiService, weatherDBDao)

    @Provides
    @Singleton
    fun provideRoomDatabase() =
        Room.databaseBuilder(
            context,
            WeatherDBRoomDatabase::class.java, "weather_table"
        ).build()

    @Provides
    fun provideDao(db: WeatherDBRoomDatabase) = db.weatherDBDao()
}