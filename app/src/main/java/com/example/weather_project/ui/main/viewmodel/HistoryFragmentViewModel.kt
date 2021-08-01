package com.example.weather_project.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weather_project.data.db.WeatherData
import com.example.weather_project.data.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryFragmentViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    val allWeatherData: LiveData<List<WeatherData>> = mainRepository.allWeatherData.asLiveData()

    fun deleteAll() = viewModelScope.launch {
        mainRepository.deleteAll()
    }
}