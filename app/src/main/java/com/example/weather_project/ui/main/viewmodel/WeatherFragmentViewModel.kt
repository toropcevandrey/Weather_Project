package com.example.weather_project.ui.main.viewmodel

import androidx.lifecycle.*
import com.example.weather_project.data.model.WeatherApiResponse
import com.example.weather_project.data.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherFragmentViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    val myResponse: MutableLiveData<WeatherApiResponse> = MutableLiveData()
    val status: MutableLiveData<Boolean?> = MutableLiveData()

    fun onButtonClicked(city: String) {
        status.value = null
            viewModelScope.launch() {
                try {
                    val response = mainRepository.getWeather(city)
                    myResponse.value = response
                    status.value = false
                }catch (e:Exception) {
                    status.value = true
                }
            }
    }
}

