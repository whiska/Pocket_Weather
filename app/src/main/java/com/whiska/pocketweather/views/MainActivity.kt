package com.whiska.pocketweather.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.matteobattilana.weather.PrecipType
import com.whiska.pocketweather.R
import com.whiska.pocketweather.databinding.ActivityMainBinding
import com.whiska.pocketweather.models.CurrentWeatherData
import com.whiska.pocketweather.models.Units
import com.whiska.pocketweather.models.WeatherForecast
import com.whiska.pocketweather.viewmodels.WeatherViewModel
import eightbitlab.com.blurview.RenderScriptBlur
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val calendar by lazy { Calendar.getInstance() }
    private val forecastAdapter by lazy { ForecastAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }

        binding.apply {

            val lat = intent.getDoubleExtra("lat", 0.0)
            val lon = intent.getDoubleExtra("lon", 0.0)
            val name = intent.getStringExtra("name")

            searchCity.setOnClickListener {
                startActivity(Intent(this@MainActivity, CityListActivity::class.java))
            }


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


            var radius = 10f
            val decorView = window.decorView
            val rootView = decorView.findViewById(android.R.id.content) as ViewGroup
            val windowBackground = decorView.background

            rootView.let {
                forecastBlurView.setupWith(it, RenderScriptBlur(this@MainActivity))
                    .setFrameClearDrawable(windowBackground)
                    .setBlurRadius(radius)
                forecastBlurView.outlineProvider = ViewOutlineProvider.BACKGROUND
                forecastBlurView.clipToOutline = true

            }

            weatherViewModel.getWeatherForecast(lat = lat, lon = lon, Units.METRIC).enqueue(object: Callback<WeatherForecast> {
                override fun onResponse(
                    call: Call<WeatherForecast>,
                    response: Response<WeatherForecast>) {

                    if(response.isSuccessful) {
                        val data = response.body()
                        forecastBlurView.visibility = View.VISIBLE
                        data?.let {
                            it.list.forEach {
                                println(it)
                            }
                            forecastAdapter.differ.submitList(it.list.toMutableList())
                            forecastList.apply {
                                layoutManager = LinearLayoutManager(
                                    this@MainActivity,
                                    LinearLayoutManager.HORIZONTAL,
                                    false)
                                adapter = forecastAdapter
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {

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