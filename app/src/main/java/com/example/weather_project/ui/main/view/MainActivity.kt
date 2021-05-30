package com.example.weather_project.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.R
import com.example.weather_project.ui.main.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var weatherFragment = WeatherFragment()
    private var historyFragment = HistoryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        firstInit()
        configureBottomNavigation()
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fl_root_container, fragment)
            .commitAllowingStateLoss()
    }

    private fun configureBottomNavigation() {
        val bottomNavigationView =
            findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_search -> changeFragment(weatherFragment)
                R.id.action_history -> changeFragment(historyFragment)
            }
            true
        }
    }
    private fun firstInit(){
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_root_container, weatherFragment)
            .commit()
    }
}