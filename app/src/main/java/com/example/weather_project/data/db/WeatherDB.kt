package com.example.weather_project.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherDB(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "temp") val temp: String?,
    @ColumnInfo(name = "date") val date: String?
) {
    constructor(city: String?, temp: String?, date: String?) : this(0, city, temp, date)
}