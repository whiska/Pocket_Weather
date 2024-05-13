package com.whiska.pocketweather.views

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.github.matteobattilana.weather.PrecipType
import com.whiska.pocketweather.R
import com.whiska.pocketweather.databinding.ActivityMainBinding
import com.whiska.pocketweather.models.CurrentWeatherData
import com.whiska.pocketweather.models.Units
import com.whiska.pocketweather.viewmodels.WeatherViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val calendar by lazy { Calendar.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }

        binding.apply {
            var lat = 51.50
            var lon = -0.12
            var name = "London"

            cityTxt.text = name
            progressBar.visibility = View.VISIBLE
            weatherViewModel.getCurrentWeatherData(lat = lat, lon = lon, Units.METRIC).enqueue(object: Callback<CurrentWeatherData> {
                override fun onResponse(
                    call: Call<CurrentWeatherData>,
                    response: Response<CurrentWeatherData>
                ) {


                    if(response.isSuccessful) {
                        val data = response.body()
                        progressBar.visibility = View.GONE
                        dataDetailLayout.visibility = View.VISIBLE
                        data?.let { weatherData ->
                            statusTxt.text = weatherData.weather[0]?.main ?: "-"
                            windTxt.text = getString(R.string.wind_speed, weatherData.wind.speed.roundToInt().toString())
                            currentTempTxt.text = getString(R.string.celsius, weatherData.main.temp.roundToInt().toString())
                            maxTempTxt.text = getString(R.string.celsius, weatherData.main.tempMax.roundToInt().toString())
                            minTempTxt.text = getString(R.string.celsius, weatherData.main.tempMin.roundToInt().toString())
                            humidityTxt.text = getString(R.string.humidity_percent, weatherData.main.humidity.toString())
                            val icon = weatherData.weather[0]?.icon ?: "-"
                            val drawable = if(isNightNow()) R.drawable.night_bg
                            else {
                                retrieveBackgroundByIcon(icon)
                            }
                            bgImg.setBackgroundResource(drawable)
                            setEffectRainSnowByIcon(icon)

                        }

                    }

                }

                override fun onFailure(call: Call<CurrentWeatherData>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_LONG).show()
                }

            })

        }
    }

    private fun isNightNow(): Boolean {
        return calendar.get(Calendar.HOUR_OF_DAY) >= 18
    }

    private fun retrieveBackgroundByIcon(icon: String): Int {
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

    private fun setEffectRainSnowByIcon(icon: String) {
        when(icon.dropLast(1)) {
            "01" -> {
                initWeatherView(PrecipType.CLEAR)
            }

            "02", "03", "04" -> {
                initWeatherView(PrecipType.CLEAR)
            }

            "09", "10", "11" -> {
                initWeatherView(PrecipType.RAIN)
            }

            "13" -> {
                initWeatherView(PrecipType.SNOW)
            }

            "50" -> {
                initWeatherView(PrecipType.CLEAR)
            }

            else -> {}

        }
    }

    private fun initWeatherView(type: PrecipType) {
        binding.weatherView.apply {
            setWeatherData(type)
            angle = -20
            emissionRate = 100.0f

        }
    }
}