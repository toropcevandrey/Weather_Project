package com.example.weather_project.data.api

import com.example.weather_project.data.model.WeatherApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") token: String = "2dbc59f0d4d33a3b6ad08dd4e19d654a",
        @Query("lang") lang: String = "ru",
        @Query("units") units: String = "metric"
    ): WeatherApiResponse
}