package com.example.weather_project.ui.main.view

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather_project.R
import com.example.weather_project.ui.App
import com.example.weather_project.ui.main.viewmodel.WeatherFragmentViewModel
import com.google.android.gms.location.*
import javax.inject.Inject

class WeatherFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private var viewModel: WeatherFragmentViewModel? = null
    private var tvChangeCity: TextView? = null
    private var tvCityName: TextView? = null
    private var tvTemp: TextView? = null
    private var tvHum: TextView? = null
    private var tvWindSpeed: TextView? = null
    private var tvWeatherInfo: TextView? = null
    private var tvTempMax: TextView? = null
    private var tvTempMin: TextView? = null
    private var pbLoading: ProgressBar? = null
    private var clWeatherContainer: ConstraintLayout? = null
    private var lon: Double = 0.0
    private var lat: Double = 0.0
    private var locationClientManager: FusedLocationProviderClient? = null
    private var LOCATION_PERMISSION_ID = 44

    companion object {
        const val TAG = "WEATHER_FRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context?.let { locationClientManager = LocationServices.getFusedLocationProviderClient(it) }
        val view: View = inflater.inflate(R.layout.weather_fragment, container, false)
        tvChangeCity = view.findViewById(R.id.tv_change_city)
        tvCityName = view.findViewById(R.id.tv_city_name)
        tvTemp = view.findViewById(R.id.tv_temp)
        pbLoading = view.findViewById(R.id.pb_loading)
        clWeatherContainer = view.findViewById(R.id.cl_weather_container)
        tvHum = view.findViewById(R.id.tv_hum)
        tvWindSpeed = view.findViewById(R.id.tv_wind_speed)
        tvWeatherInfo = view.findViewById(R.id.tv_weather_info)
        tvTempMax = view.findViewById(R.id.tv_temp_max)
        tvTempMin = view.findViewById(R.id.tv_temp_min)
        App.getComponent().inject(this)
        getLastLocation()
        setupWeatherViewModel()
        setObservers()
        tvChangeCity?.setOnClickListener {
            showDialog()
        }

        return view
    }

    private fun setupWeatherViewModel() {
        Log.d("mytag", "setupWeatherViewModel()")
        viewModel = ViewModelProvider(this, factory).get(WeatherFragmentViewModel::class.java)
    }

    private fun firstCreate() {
        Log.d("mytag", "firstCreate()")
        viewModel?.firstInit(lat, lon)
    }

    private fun setObservers() {
        viewModel?.myResponse?.observe(viewLifecycleOwner, Observer { response ->
            tvCityName?.text = response.name
            tvTemp?.text = (response.main.temp.toInt().toString() + "°C")
            tvHum?.text = (response.main.humidity.toString() + "%")
            tvWindSpeed?.text = (response.wind.speed.toString() + "м/с")
            tvWeatherInfo?.text = response.weather[0].description
            tvTempMax?.text = (response.main.tempMax.toInt().toString() + "°")
            tvTempMin?.text = (response.main.tempMin.toInt().toString() + "°")
            when (response.weather[0].icon) {
                "01d", "01n" -> {
                    clWeatherContainer?.setBackgroundResource(R.drawable.d01)
                }
                "02d", "02n" -> {
                    clWeatherContainer?.setBackgroundResource(R.drawable.d02)
                }
                "03d", "03n" -> {
                    clWeatherContainer?.setBackgroundResource(R.drawable.d03)
                }
                "04d", "04n" -> {
                    clWeatherContainer?.setBackgroundResource(R.drawable.d04)
                }
                "09d", "09n" -> {
                    clWeatherContainer?.setBackgroundResource(R.drawable.d09)
                }
                "10d", "10n" -> {
                    clWeatherContainer?.setBackgroundResource(R.drawable.d10)
                }
                "11d", "11n" -> {
                    clWeatherContainer?.setBackgroundResource(R.drawable.d11)
                }
                "12d", "12n" -> {
                    clWeatherContainer?.setBackgroundResource(R.drawable.d12)
                }
                "50d", "50n" -> {
                    clWeatherContainer?.setBackgroundResource(R.drawable.d50)
                }
            }
        })

        viewModel?.status?.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                false -> {
                    pbLoading?.visibility = View.GONE
                    clWeatherContainer?.visibility = View.VISIBLE
                }
                null -> {
                    pbLoading?.visibility = View.VISIBLE
                    clWeatherContainer?.visibility = View.GONE
                }
                true -> {
                    pbLoading?.visibility = View.GONE
                    clWeatherContainer?.visibility = View.VISIBLE
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_ID &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            getLastLocation()
        }
    }

    private fun requestLocationPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            LOCATION_PERMISSION_ID
        )
    }

    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (context?.let {
                        ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    } != PackageManager.PERMISSION_GRANTED && context?.let {
                        ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    } != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                locationClientManager?.lastLocation?.addOnCompleteListener { task ->
                    val location = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        lat = location.latitude
                        lon = location.longitude
                    }
                    firstCreate()
                }
            } else {
                showLocationPermissionsSettings()
            }
        } else {
            requestLocationPermissions()
        }
    }

    private fun checkPermissions(): Boolean =
        context?.let {
            return ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
        } ?: false

    private fun isLocationEnabled(): Boolean {
        return context?.let {
            val locationManager = ContextCompat.getSystemService(it, LocationManager::class.java)
            return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false ||
                    locationManager?.isProviderEnabled(
                        LocationManager.NETWORK_PROVIDER
                    ) ?: false
        } ?: false
    }

    private fun requestNewLocationData() {
        context?.let {
            val mLocationRequest = LocationRequest.create().apply {
                interval = 100
                fastestInterval = 50
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                maxWaitTime = 100
            }
            mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest.interval = 5
            mLocationRequest.fastestInterval = 0
            mLocationRequest.numUpdates = 1

            locationClientManager = LocationServices.getFusedLocationProviderClient(it)
            if (ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            locationClientManager?.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.myLooper()
            )
        }
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation
            lat = mLastLocation.latitude
            lon = mLastLocation.longitude
            firstCreate()
        }
    }

    private fun showLocationPermissionsSettings() {
        try {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        } catch (e: Exception) {
        }
    }

    private fun showDialog() {
        Log.d("mytag", "showDialog()")
        val dialog: Dialog
        context?.let {
            dialog = Dialog(it)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.custom_dialog_fragment)
            val etCityName = dialog.findViewById(R.id.et_city_name) as EditText
            val btnSearch = dialog.findViewById(R.id.btn_search) as Button
            btnSearch.setOnClickListener {
                viewModel?.onButtonClicked(etCityName.text.toString())
                dialog.dismiss()
            }
            dialog.show()

        }
    }
}