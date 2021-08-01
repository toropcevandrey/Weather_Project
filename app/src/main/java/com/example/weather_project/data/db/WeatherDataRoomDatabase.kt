package com.example.weather_project.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherData::class], version = 1, exportSchema = false)
abstract class WeatherDataRoomDatabase : RoomDatabase() {
    abstract fun weatherDataDao(): WeatherDataDao
}