package com.example.weather_project.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.data.api.ApiHelper
import com.example.weather_project.data.api.ApiService
import com.example.weather_project.ui.main.viewmodel.WeatherFragmentViewModel

class WeatherViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherFragmentViewModel::class.java)) {
            return WeatherFragmentViewModel(ApiHelper(apiService)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}