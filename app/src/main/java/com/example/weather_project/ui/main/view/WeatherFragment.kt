package com.example.weather_project.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.R
import com.example.weather_project.data.api.ApiHelper
import com.example.weather_project.data.api.RetrofitBuilder
import com.example.weather_project.ui.base.WeatherViewModelFactory
import com.example.weather_project.ui.main.viewmodel.WeatherFragmentViewModel

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherFragmentViewModel
    private lateinit var cityName: TextView
    private lateinit var btnSearch: Button
    private lateinit var etSearch: EditText
    private lateinit var tvTemp: TextView
    private lateinit var hum: TextView
    private lateinit var wind: TextView
    private lateinit var info: TextView
    private lateinit var maxTemp: TextView
    private lateinit var minTemp: TextView
    private lateinit var pgBar: ProgressBar
    private lateinit var cLayout: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.weather_fragment, container, false)
        cityName = view.findViewById(R.id.cityName)
        btnSearch = view.findViewById(R.id.btnSearch)
        etSearch = view.findViewById(R.id.etSearch)
        tvTemp = view.findViewById(R.id.tvTemp)
        pgBar = view.findViewById(R.id.pgBar)
        cLayout = view.findViewById(R.id.clayout)
        hum = view.findViewById(R.id.hum)
        wind = view.findViewById(R.id.windSpeed)
        info = view.findViewById(R.id.wInfo)
        maxTemp = view.findViewById(R.id.tempMax)
        minTemp = view.findViewById(R.id.tempMin)
        btnSearch.setOnClickListener {
            onButtonClick()
        }
        setupWeatherViewModel()
        setObservers()
        return view
    }

    private fun setupWeatherViewModel() {
        viewModel = ViewModelProvider(
            this,
            WeatherViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(WeatherFragmentViewModel::class.java)
    }

    private fun onButtonClick() {
        viewModel.onButtonClicked(etSearch.text.toString())
        etSearch.setText("")
    }

    private fun setObservers() {
        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            cityName.text = response.name
            tvTemp.text = (response.main.temp.toInt().toString() + "°C")
            hum.text = (response.main.humidity.toString() + "%")
            wind.text = (response.wind.speed.toString() + "м/с")
            info.text = response.weather[0].description
            maxTemp.text = (response.main.temp_max.toInt().toString() + "°")
            minTemp.text = (response.main.temp_min.toInt().toString() + "°")
            when (response.weather[0].icon) {
                "01d", "01n" -> {
                    cLayout.setBackgroundResource(R.drawable.d01)
                }
                "02d", "02n" -> {
                    cLayout.setBackgroundResource(R.drawable.d02)
                }
                "03d", "03n" -> {
                    cLayout.setBackgroundResource(R.drawable.d03)
                }
                "04d", "04n" -> {
                    cLayout.setBackgroundResource(R.drawable.d04)
                }
                "09d", "09n" -> {
                    cLayout.setBackgroundResource(R.drawable.d09)
                }
                "10d", "10n" -> {
                    cLayout.setBackgroundResource(R.drawable.d10)
                }
                "11d", "11n" -> {
                    cLayout.setBackgroundResource(R.drawable.d11)
                }
                "12d", "12n" -> {
                    cLayout.setBackgroundResource(R.drawable.d12)
                }
                "50d", "50n" -> {
                    cLayout.setBackgroundResource(R.drawable.d50)
                }
            }
        })

        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                false -> {
                    pgBar.visibility = View.GONE
                    cLayout.visibility = View.VISIBLE
                }
                null -> {
                    pgBar.visibility = View.VISIBLE
                    cLayout.visibility = View.GONE
                }
                true -> {
                    pgBar.visibility = View.GONE
                    cLayout.visibility = View.VISIBLE
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}