package com.whiska.pocketweather.views

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.whiska.pocketweather.databinding.CityViewBinding
import com.whiska.pocketweather.models.CityData
import com.whiska.pocketweather.models.WeatherForecast

class CityListAdapter(val context: Context) : RecyclerView.Adapter<CityListAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = CityViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val binding = holder.binding
        val city = differ.currentList[position]
        binding.cityTxt.text = city.name

        binding.root.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("lat", city.lat)
            intent.putExtra("lon", city.lon)
            intent.putExtra("name", city.name)
            context.startActivity(intent)
        }


    }

    private val diffCallBack = object: DiffUtil.ItemCallback<CityData.CityItem>() {

        override fun areItemsTheSame(oldItem: CityData.CityItem, newItem: CityData.CityItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CityData.CityItem, newItem: CityData.CityItem): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallBack)


    class CityViewHolder(val binding: CityViewBinding) : RecyclerView.ViewHolder(binding.root) {

    }




}