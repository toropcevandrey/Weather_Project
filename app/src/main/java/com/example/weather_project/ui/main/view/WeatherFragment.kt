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
import com.example.weather_project.ui.App
import com.example.weather_project.ui.main.viewmodel.WeatherFragmentViewModel
import javax.inject.Inject

class WeatherFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var viewModel: WeatherFragmentViewModel
    private lateinit var tvCityName: TextView
    private lateinit var btnSearch: Button
    private lateinit var tietSearch: EditText
    private lateinit var tvTemp: TextView
    private lateinit var tvHum: TextView
    private lateinit var tvWindSpeed: TextView
    private lateinit var tvWeatherInfo: TextView
    private lateinit var tvTempMax: TextView
    private lateinit var tvTempMin: TextView
    private lateinit var pbLoading: ProgressBar
    private lateinit var clWeatherContainer: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.weather_fragment, container, false)
        tvCityName = view.findViewById(R.id.tv_city_name)
        btnSearch = view.findViewById(R.id.btn_search)
        tietSearch = view.findViewById(R.id.tiet_search)
        tvTemp = view.findViewById(R.id.tv_temp)
        pbLoading = view.findViewById(R.id.pb_loading)
        clWeatherContainer = view.findViewById(R.id.cl_weather_container)
        tvHum = view.findViewById(R.id.tv_hum)
        tvWindSpeed = view.findViewById(R.id.tv_wind_speed)
        tvWeatherInfo = view.findViewById(R.id.tv_weather_info)
        tvTempMax = view.findViewById(R.id.tv_temp_max)
        tvTempMin = view.findViewById(R.id.tv_temp_min)
        App.getComponent().inject(this)
        btnSearch.setOnClickListener {
            onButtonClick()
        }
        setupWeatherViewModel()
        setObservers()
        return view
    }

    private fun setupWeatherViewModel() {
        viewModel = ViewModelProvider(this, factory).get(WeatherFragmentViewModel::class.java)
    }

    private fun onButtonClick() {
        viewModel.onButtonClicked(tietSearch.text.toString())
        tietSearch.setText("")
    }

    private fun setObservers() {
        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            tvCityName.text = response.name
            tvTemp.text = (response.main.temp.toInt().toString() + "°C")
            tvHum.text = (response.main.humidity.toString() + "%")
            tvWindSpeed.text = (response.wind.speed.toString() + "м/с")
            tvWeatherInfo.text = response.weather[0].description
            tvTempMax.text = (response.main.tempMax.toInt().toString() + "°")
            tvTempMin.text = (response.main.tempMin.toInt().toString() + "°")
            when (response.weather[0].icon) {
                "01d", "01n" -> {
                    clWeatherContainer.setBackgroundResource(R.drawable.d01)
                }
                "02d", "02n" -> {
                    clWeatherContainer.setBackgroundResource(R.drawable.d02)
                }
                "03d", "03n" -> {
                    clWeatherContainer.setBackgroundResource(R.drawable.d03)
                }
                "04d", "04n" -> {
                    clWeatherContainer.setBackgroundResource(R.drawable.d04)
                }
                "09d", "09n" -> {
                    clWeatherContainer.setBackgroundResource(R.drawable.d09)
                }
                "10d", "10n" -> {
                    clWeatherContainer.setBackgroundResource(R.drawable.d10)
                }
                "11d", "11n" -> {
                    clWeatherContainer.setBackgroundResource(R.drawable.d11)
                }
                "12d", "12n" -> {
                    clWeatherContainer.setBackgroundResource(R.drawable.d12)
                }
                "50d", "50n" -> {
                    clWeatherContainer.setBackgroundResource(R.drawable.d50)
                }
            }
        })

        viewModel.status.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                false -> {
                    pbLoading.visibility = View.GONE
                    clWeatherContainer.visibility = View.VISIBLE
                }
                null -> {
                    pbLoading.visibility = View.VISIBLE
                    clWeatherContainer.visibility = View.GONE
                }
                true -> {
                    pbLoading.visibility = View.GONE
                    clWeatherContainer.visibility = View.VISIBLE
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}