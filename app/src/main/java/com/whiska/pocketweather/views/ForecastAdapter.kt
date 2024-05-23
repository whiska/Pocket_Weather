package com.whiska.pocketweather.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.whiska.pocketweather.R
import com.whiska.pocketweather.databinding.ForecastViewBinding
import com.whiska.pocketweather.models.WeatherForecast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ForecastAdapter(val context: Context) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ForecastViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val binding = holder.binding
        val forecast = differ.currentList[position]
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(forecast.dtTxt)
        val calendar = Calendar.getInstance()
        calendar.time = date!!
        val dayOfWeekName =
            when(calendar.get(Calendar.DAY_OF_WEEK)) {
                1 -> "Sun"
                2 -> "Mon"
                3 -> "Tue"
                4 -> "Wed"
                5 -> "Thu"
                6 -> "Fri"
                7 -> "Sat"
                else -> "-"
            }

        binding.weekDayTxt.text = dayOfWeekName
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val amPm = if(hour >= 12) "pm" else "am"
        val hour12 = calendar.get(Calendar.HOUR)
        binding.hourTxt.text = context.getString(R.string.hour_am_pm, hour12.toString(), amPm)
        binding.tempTxt.text = context.getString(R.string.celsius, forecast.main.temp.let { Math.round(it) }.toString())

        val icon: Int = when(forecast.weather.get(0).icon) {
            "01d", "0n" -> R.drawable.sunny
            "02d", "02n" -> R.drawable.cloudy_sunny
            "03d", "03n" -> R.drawable.cloudy_sunny
            "04d", "04n" -> R.drawable.cloudy
            "09d", "09n" -> R.drawable.rainy
            "10d", "10n" -> R.drawable.rainy
            "11d", "11n" -> R.drawable.storm
            "13d", "13n" -> R.drawable.snowy
            "50d", "50n" -> R.drawable.windy
            else -> R.drawable.sunny
        }

        Glide.with(context).load(icon).into(binding.weatherImg)



    }

    override fun getItemCount() = differ.currentList.size

    private val diffCallBack = object: DiffUtil.ItemCallback<WeatherForecast.Forecast>() {
        override fun areItemsTheSame(oldItem: WeatherForecast.Forecast, newItem: WeatherForecast.Forecast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: WeatherForecast.Forecast,
            newItem: WeatherForecast.Forecast
        ): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallBack)

    class ForecastViewHolder(val binding: ForecastViewBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}