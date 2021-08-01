package com.example.weather_project.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDBDao {

    @Query("SELECT * FROM weather_table ORDER BY id ASC")
    fun getAlphabetized(): Flow<List<WeatherDB>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weatherDB: WeatherDB)

    @Query("DELETE FROM weather_table")
    suspend fun deleteAll()
}