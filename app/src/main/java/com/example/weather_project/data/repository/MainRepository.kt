package com.example.weather_project.data.repository

import com.example.weather_project.data.api.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getWeather(lat: Double, lon: Double) = apiService.getWeather(lat, lon)
    suspend fun getWeather(city: String) = apiService.getWeather(city)
}