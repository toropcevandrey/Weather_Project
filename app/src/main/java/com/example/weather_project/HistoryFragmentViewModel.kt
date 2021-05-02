package com.example.weather_project

import androidx.lifecycle.ViewModel

class HistoryFragmentViewModel: ViewModel() {


    override fun onCleared() {
        super.onCleared()
        // Dispose All your Subscriptions to avoid memory leaks
    }

}