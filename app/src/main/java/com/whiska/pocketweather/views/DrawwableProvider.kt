package com.whiska.pocketweather.views

import com.whiska.pocketweather.R

fun retrieveBackgroundByIcon(icon: String): Int {
    return when(icon.dropLast(1)) {
        "01" -> {
            R.drawable.snow_bg
        }

        "02", "03", "04" -> {
            R.drawable.cloudy_bg
        }

        "09", "10", "11" -> {
            R.drawable.rainy_bg
        }

        "13" -> {
            R.drawable.snow_bg
        }

        "50" -> {
            R.drawable.haze_bg
        }

        else -> 0
    }
}