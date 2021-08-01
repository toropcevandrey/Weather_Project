package com.example.weather_project.ui.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_project.R
import com.example.weather_project.data.db.WeatherData

class HistoryListAdapter :
    ListAdapter<WeatherData, HistoryListAdapter.HistoryViewHolder>(WeatherDataComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.date, current.city, current.temp)
    }

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvHistoryDate: TextView = itemView.findViewById(R.id.tv_history_date)
        private val tvHistoryCity: TextView = itemView.findViewById(R.id.tv_history_city)
        private val tvHistoryTemp: TextView = itemView.findViewById(R.id.tv_history_temp)

        fun bind(date: String?, city: String?, temp: String?) {
            tvHistoryDate.text = date
            tvHistoryCity.text = city
            tvHistoryTemp.text = temp
        }

        companion object {
            fun create(parent: ViewGroup): HistoryViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_history, parent, false)
                return HistoryViewHolder(view)
            }
        }
    }

    class WeatherDataComparator : DiffUtil.ItemCallback<WeatherData>() {
        override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
            return oldItem == newItem
        }
    }
}