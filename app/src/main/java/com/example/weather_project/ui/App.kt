package com.example.weather_project.ui

import android.app.Application
import com.example.weather_project.di.components.AppComponent
import com.example.weather_project.di.components.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.create()
    }
    companion object{
        private lateinit var component:AppComponent

        fun getComponent():AppComponent{
            return component
        }
    }
}