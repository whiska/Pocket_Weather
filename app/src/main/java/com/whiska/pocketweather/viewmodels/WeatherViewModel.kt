package com.whiska.pocketweather.viewmodels

import androidx.lifecycle.ViewModel
import com.whiska.pocketweather.models.WeatherRepository
import com.whiska.pocketweather.models.Units

class WeatherViewModel: ViewModel() {

    private val apiRepository = WeatherRepository()


    fun getCurrentWeatherData(lat: Double, lon: Double, units: Units) =
        apiRepository.getCurrentWeatherData(lat, lon, units.name)
}