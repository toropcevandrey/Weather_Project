package com.example.weather_project.ui.main.view

import androidx.recyclerview.widget.DiffUtil
import com.example.weather_project.data.db.WeatherData

class WeatherDataComparator : DiffUtil.ItemCallback<WeatherData>() {
    override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
        return oldItem == newItem
    }
}