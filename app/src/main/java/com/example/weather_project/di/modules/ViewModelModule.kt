package com.example.weather_project.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.di.ViewModelKey
import com.example.weather_project.ui.base.ViewModelFactory
import com.example.weather_project.ui.main.viewmodel.MainViewModel
import com.example.weather_project.ui.main.viewmodel.WeatherFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(WeatherFragmentViewModel::class)
    @Singleton
    abstract fun bindWeatherViewModel(weatherFragmentViewModel: WeatherFragmentViewModel): ViewModel

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Singleton
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

}