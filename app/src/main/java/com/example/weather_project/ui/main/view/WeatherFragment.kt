package com.example.weather_project.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.R
import com.example.weather_project.data.api.RetrofitBuilder
import com.example.weather_project.ui.base.WeatherViewModelFactory
import com.example.weather_project.ui.main.viewmodel.WeatherFragmentViewModel

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherFragmentViewModel
    private lateinit var cityName: TextView
    private lateinit var btnSearch: Button
    private lateinit var etSearch: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.weather_fragment, container, false)
        cityName = view.findViewById(R.id.cityName)
        btnSearch = view.findViewById(R.id.btnSearch)
        etSearch = view.findViewById(R.id.etSearch)
        btnSearch.setOnClickListener {
            onButtonClick()
        }
        setupWeatherViewModel()

        return view
    }

    private fun setupWeatherViewModel() {
        viewModel = ViewModelProvider(
            this,
            WeatherViewModelFactory((RetrofitBuilder.apiService))
        ).get(WeatherFragmentViewModel::class.java)
    }

    private fun onButtonClick() {
        viewModel.onButtonClicked(etSearch.text.toString())
        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            cityName.text = response.name
        })
    }
}