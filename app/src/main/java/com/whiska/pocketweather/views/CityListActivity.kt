package com.whiska.pocketweather.views

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import com.whiska.pocketweather.databinding.ActivityCityListBinding
import com.whiska.pocketweather.databinding.ActivityMainBinding

class CityListActivity : ComponentActivity() {

    private lateinit var binding: ActivityCityListBinding
    private val cityAdapter by lazy { City}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}