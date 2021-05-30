package com.example.weather_project.di.components

import com.example.weather_project.di.modules.MainModule
import com.example.weather_project.di.modules.NetworkModule
import com.example.weather_project.di.modules.ViewModelModule
import com.example.weather_project.ui.main.view.WeatherFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class, NetworkModule::class, MainModule::class])
interface AppComponent {
    fun inject(fragment: WeatherFragment)
}