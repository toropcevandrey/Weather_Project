package com.example.weather_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    var weatherFragment = WeatherFragment()
    var historyFragment = HistoryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_search -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.root_container, weatherFragment)
                            .commitAllowingStateLoss()
                }
                R.id.action_history -> {
                    supportFragmentManager.beginTransaction()
                            .replace(R.id.root_container, historyFragment)
                            .commitAllowingStateLoss()
                }
            }
            true
        }

    }
}