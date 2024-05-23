package com.whiska.pocketweather.viewmodels

import androidx.lifecycle.ViewModel
import com.whiska.pocketweather.models.CitiesRepository

class CitiesViewModel :ViewModel() {

    private val apiRepository = CitiesRepository()

    fun getCities(q: String, limit: Int) = apiRepository.getCities(q, limit)
}