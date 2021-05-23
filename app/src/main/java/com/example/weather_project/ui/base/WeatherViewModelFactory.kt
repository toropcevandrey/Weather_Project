package com.example.weather_project.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.data.api.ApiHelper
import com.example.weather_project.data.repository.MainRepository
import com.example.weather_project.ui.main.viewmodel.WeatherFragmentViewModel

class WeatherViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherFragmentViewModel::class.java)) {
            return WeatherFragmentViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}