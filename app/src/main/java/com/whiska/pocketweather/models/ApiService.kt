package com.whiska.pocketweather.models

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/weather")
    fun getCurrentWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Call<CurrentWeatherData>

    @GET("data/2.5/forecast")
    fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Call<WeatherForecast>

    @GET("geo/1.0/direct")
    fun getCity(
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("appid") appKey: String
    ): Call<CityData>
}