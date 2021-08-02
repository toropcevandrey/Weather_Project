package com.example.weather_project.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDataDao {

    @Query("SELECT * FROM weather_table ORDER BY id DESC")
    fun getAlphabetized(): Flow<List<WeatherData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weatherData: WeatherData)

    @Query("DELETE FROM weather_table")
    suspend fun deleteAll()
}