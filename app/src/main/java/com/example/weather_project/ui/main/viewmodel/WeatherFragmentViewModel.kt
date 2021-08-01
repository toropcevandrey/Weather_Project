package com.example.weather_project.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.weather_project.data.db.WeatherDB
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

    fun firstInit(lat: Double, lon: Double) {
        Log.d("mytag", "firstInit()")
        Log.d("mytag", "lat - $lat")
        Log.d("mytag", "long - $lon")
        status.value = null
        viewModelScope.launch() {
            try {
                val response = mainRepository.getWeatherByCoord(lat, lon)
                myResponse.value = response
                status.value = false
            } catch (e: Exception) {
                status.value = true
            }
        }
    }

    fun onButtonClicked(city: String) {
        Log.d("mytag", "onButtonClicked()")
        Log.d("mytag", "city - $city")
        status.value = null
        viewModelScope.launch() {
            try {
                val response = mainRepository.getWeatherByCity(city)
                myResponse.value = response
                status.value = false
                saveData(
                    WeatherDB(
                        city = response.name,
                        temp = response.main.temp.toInt().toString(),
                        date = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString()
                    )
                )
            } catch (e: Exception) {
                status.value = true
            }
        }
    }

    private suspend fun saveData(weatherDB: WeatherDB) {
        Log.d("mytag", "saveData()")
        mainRepository.insert(weatherDB)
    }

}

