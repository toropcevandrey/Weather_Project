package com.example.weather_project.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {
    init {
        Log.d("viewModel", "mainActivity")
    }
}