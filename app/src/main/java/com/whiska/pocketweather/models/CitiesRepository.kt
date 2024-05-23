package com.whiska.pocketweather.models

class CitiesRepository {

    fun getCities(q: String, limit: Int) = ApiClient.apiService.getCity(q, limit, "ae48f857d6364899772f33ab7252fe9b")
}