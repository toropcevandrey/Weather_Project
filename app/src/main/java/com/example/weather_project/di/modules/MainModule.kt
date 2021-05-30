package com.example.weather_project.di.modules

import com.example.weather_project.data.api.ApiService
import com.example.weather_project.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainModule {

    @Provides
    @Singleton
    fun providesMainRepository(apiService: ApiService) = MainRepository(apiService)
}