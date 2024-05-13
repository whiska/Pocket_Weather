package com.whiska.pocketweather.viewmodels

import androidx.lifecycle.ViewModel
import com.whiska.pocketweather.models.ApiClient
import com.whiska.pocketweather.models.ApiRepository
import com.whiska.pocketweather.models.ApiService
import com.whiska.pocketweather.models.Units

class CurrentWeatherViewModel: ViewModel() {

    private val apiRepository = ApiRepository()
    private val apiKeyRepository = ApiKeyRepository()


    fun getCurrentWeatherData(lat: Double, lon: Double, units: Units) {
        apiRepository.getCurrentWeatherData(lat, lon, units.name, )
    }
}