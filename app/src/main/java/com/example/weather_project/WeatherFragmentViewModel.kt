package com.example.weather_project


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.security.auth.callback.Callback


class WeatherFragmentViewModel:ViewModel() {


    override fun onCleared() {
        super.onCleared()
        // Dispose All your Subscriptions to avoid memory leaks
    }


}