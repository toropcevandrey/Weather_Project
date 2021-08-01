package com.example.weather_project.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WeatherDB::class], version = 1, exportSchema = false)
abstract class WeatherDBRoomDatabase : RoomDatabase() {
    abstract fun weatherDBDao() : WeatherDBDao
}