package com.example.weather_project.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getWeather(city: String) = apiService.getWeather(city)
}