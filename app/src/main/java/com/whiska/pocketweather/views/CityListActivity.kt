package com.whiska.pocketweather.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.whiska.pocketweather.databinding.ActivityCityListBinding
import com.whiska.pocketweather.models.CityData
import com.whiska.pocketweather.viewmodels.CityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityListActivity : ComponentActivity() {

    private lateinit var binding: ActivityCityListBinding
    private val cityAdapter by lazy { CityListAdapter(this) }
    private val cityViewModel : CityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            searchCityInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    s?.let {
                        cityViewModel.getCities(it.toString(), 10).enqueue(object : Callback<CityData> {
                            override fun onResponse(
                                call: Call<CityData>,
                                response: Response<CityData>
                            ) {
                                val data = response.body()
                                data?.let {


                                    cityAdapter.differ.submitList(it)
                                    cityList.apply {
                                        layoutManager = LinearLayoutManager(this@CityListActivity, LinearLayoutManager.VERTICAL, false)
                                        adapter = cityAdapter
                                    }
                                }

                            }

                            override fun onFailure(call: Call<CityData>, t: Throwable) {

                            }

                        })
                    }

                }

            })
        }
    }
}