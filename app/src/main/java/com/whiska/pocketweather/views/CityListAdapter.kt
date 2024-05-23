package com.whiska.pocketweather.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.whiska.pocketweather.databinding.CityViewBinding
import com.whiska.pocketweather.models.City
import com.whiska.pocketweather.models.WeatherForecast

class CityListAdapter : RecyclerView.Adapter<CityListAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = CityViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val binding = holder.binding
        binding.root.setOnClickListener {

        }


    }

    private val diffCallBack = object: DiffUtil.ItemCallback<City>() {

        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffCallBack)


    class CityViewHolder(val binding: CityViewBinding) : RecyclerView.ViewHolder(binding.root) {

    }




}