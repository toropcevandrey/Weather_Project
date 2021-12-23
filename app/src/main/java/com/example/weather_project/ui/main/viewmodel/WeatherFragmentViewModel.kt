package com.example.weather_project.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.weather_project.data.db.WeatherData
import com.example.weather_project.data.model.WeatherApiResponse
import com.example.weather_project.data.repository.MainRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class WeatherFragmentViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    val myResponse: MutableLiveData<WeatherApiResponse> = MutableLiveData()
    val status: MutableLiveData<Boolean?> = MutableLiveData()
    private var response: WeatherApiResponse? = null

    fun firstInit(lat: Double, lon: Double) {
        status.value = null
        if (response == null) {
            viewModelScope.launch() {
                try {
                    response = mainRepository.getWeatherByCoord(lat, lon)
                    myResponse.value = response
                    status.value = false
                    saveData(
                        WeatherData(
                            city = response?.name,
                            temp = response?.main?.temp?.toInt().toString(),
                            date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
                                .toString()
                        )
                    )
                } catch (e: Exception) {
                    status.value = true
                }
            }
        } else {
            status.value = false
        }
    }

    fun onMyCoord(lat: Double, lon: Double) {
        status.value = null
        viewModelScope.launch() {
            try {
                response = mainRepository.getWeatherByCoord(lat, lon)
                myResponse.value = response
                status.value = false
                saveData(
                    WeatherData(
                        city = response?.name,
                        temp = response?.main?.temp?.toInt().toString(),
                        date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString()
                    )
                )
            } catch (e: Exception) {
                status.value = true
            }
        }
    }

    fun onButtonClicked(city: String) {
        status.value = null
        viewModelScope.launch() {
            try {
                response = mainRepository.getWeatherByCity(city)
                myResponse.value = response
                status.value = false
                saveData(
                    WeatherData(
                        city = response?.name,
                        temp = response?.main?.temp?.toInt().toString(),
                        date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString()
                    )
                )
            } catch (e: Exception) {
                status.value = true
            }
        }
    }

    private suspend fun saveData(weatherData: WeatherData) {
        mainRepository.insert(weatherData)
    }

}

