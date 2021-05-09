package com.example.weather_project.ui.main.viewmodel

import androidx.lifecycle.*
import com.example.weather_project.data.api.ApiHelper
import com.example.weather_project.data.model.WeatherApiResponse
import kotlinx.coroutines.launch


class WeatherFragmentViewModel(
    private val apiHelper: ApiHelper
) : ViewModel() {

    val myResponse: MutableLiveData<WeatherApiResponse> = MutableLiveData()

    fun onButtonClicked(city: String) {
        viewModelScope.launch() {
            val response = apiHelper.getWeather(city)
            myResponse.value = response
        }
    }
}


