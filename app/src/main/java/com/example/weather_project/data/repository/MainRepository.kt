package com.example.weather_project.data.repository

import com.example.weather_project.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getWeather(city: String) = apiHelper.getWeather(city)
}