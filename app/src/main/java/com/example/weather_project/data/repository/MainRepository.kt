package com.example.weather_project.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.weather_project.data.api.ApiService
import com.example.weather_project.data.db.WeatherDB
import com.example.weather_project.data.db.WeatherDBDao
import com.example.weather_project.data.model.WeatherApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val weatherDBDao: WeatherDBDao
) {
    val allWeatherDB: Flow<List<WeatherDB>> = weatherDBDao.getAlphabetized()

    @WorkerThread
    suspend fun insert(weatherDB: WeatherDB) {
        Log.d("mytag", "insert()")
        weatherDBDao.insert(weatherDB)
    }

    @WorkerThread
    suspend fun deleteAll() {
        Log.d("mytag", "deleteAll()")
        weatherDBDao.deleteAll()
    }

    suspend fun getWeatherByCoord(lat: Double, lon: Double): WeatherApiResponse {
        Log.d("mytag", "getWeatherByCoord()")
        return apiService.getWeather(lat, lon)
    }

    suspend fun getWeatherByCity(city: String): WeatherApiResponse {
        Log.d("mytag", "getWeatherByCity()")
        return apiService.getWeather(city)
    }
}