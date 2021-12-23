package com.example.weather_project.data.repository

import androidx.annotation.WorkerThread
import com.example.weather_project.data.api.ApiService
import com.example.weather_project.data.db.WeatherData
import com.example.weather_project.data.db.WeatherDataDao
import com.example.weather_project.data.model.WeatherApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val weatherDataDao: WeatherDataDao
) {
    val allWeatherData: Flow<List<WeatherData>> = weatherDataDao.getAlphabetized()

    @WorkerThread
    suspend fun insert(weatherData: WeatherData) {
        weatherDataDao.insert(weatherData)
    }

    @WorkerThread
    suspend fun deleteAll() {
        weatherDataDao.deleteAll()
    }

    suspend fun getWeatherByCoord(lat: Double, lon: Double): WeatherApiResponse {
        return apiService.getWeather(lat, lon)
    }

    suspend fun getWeatherByCity(city: String): WeatherApiResponse {
        return apiService.getWeather(city)
    }
}