package com.example.weather_project.ui

import android.app.Application
import com.example.weather_project.di.components.AppComponent
import com.example.weather_project.di.components.DaggerAppComponent
import com.example.weather_project.di.modules.MainModule
import com.example.weather_project.di.modules.NetworkModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder().mainModule(MainModule(applicationContext))
            .networkModule(NetworkModule()).build()
    }

    companion object {
        private lateinit var component: AppComponent

        fun getComponent(): AppComponent {
            return component
        }
    }
}