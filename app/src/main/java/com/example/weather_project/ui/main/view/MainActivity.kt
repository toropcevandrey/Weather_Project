package com.example.weather_project.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.R
import com.example.weather_project.data.utils.extensions.attach
import com.example.weather_project.data.utils.extensions.detach
import com.example.weather_project.ui.App
import com.example.weather_project.ui.main.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.getComponent().inject(this)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        firstInit()
        configureBottomNavigation()
    }

    private fun changeFragment(tag: String) {
        val item = supportFragmentManager.findFragmentByTag(tag) ?: createFragmentByTag(tag)
        if (item.isAdded) return
        supportFragmentManager.detach(R.id.fl_root_container)
        supportFragmentManager.attach(item, tag, R.id.fl_root_container)
        supportFragmentManager.executePendingTransactions()
    }

    private fun createFragmentByTag(tag: String): Fragment =
        if (tag == WeatherFragment.TAG) {
            WeatherFragment()
        } else {
            HistoryFragment()
        }

    private fun configureBottomNavigation() {
        val bottomNavigationView =
            findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_search -> changeFragment(WeatherFragment.TAG)
                R.id.action_history -> changeFragment(HistoryFragment.TAG)
            }
            true
        }
    }

    private fun firstInit() {
        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_root_container, WeatherFragment())
                .commit()
        }
    }
}