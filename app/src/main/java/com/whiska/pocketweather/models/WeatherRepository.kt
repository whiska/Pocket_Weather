package com.whiska.pocketweather.models

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherRepository {

    fun getCurrentWeatherData(lat: Double, lon: Double, units: String) = ApiClient.apiService.getCurrentWeatherData(lat, lon, units, "ae48f857d6364899772f33ab7252fe9b")
    fun getWeatherForecast(lat: Double, lon: Double, units: String) = ApiClient.apiService.getWeatherForecast(lat, lon, units, "ae48f857d6364899772f33ab7252fe9b")




}