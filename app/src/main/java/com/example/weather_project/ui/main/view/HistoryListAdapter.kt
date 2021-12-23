package com.example.weather_project.ui.main.view

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.weather_project.data.db.WeatherData

class HistoryListAdapter :
    ListAdapter<WeatherData, HistoryViewHolder>(WeatherDataComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.date, current.city, current.temp)
    }

}