package com.example.weather_project.di.modules

import com.example.weather_project.data.api.RetrofitBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitBuilder() = RetrofitBuilder.apiService
}