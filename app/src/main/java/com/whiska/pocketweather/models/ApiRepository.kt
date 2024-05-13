package com.whiska.pocketweather.models

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository() {

    fun getCurrentWeatherData(lat: Double, lon: Double, units: String, apiKey: String) {
        val call = ApiClient.apiService.getCurrentWeatherData(lat, lon, units, apiKey)
        call.enqueue(object: Callback<CurrentWeatherData> {
            override fun onResponse(
                call: Call<CurrentWeatherData>,
                response: Response<CurrentWeatherData>
            ) {
                if(response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        return ApiClient.apiService.getCurrentWeatherData(lat, lon, units, apiKey).execute().body() ?: CurrentWeatherData()
    }
}