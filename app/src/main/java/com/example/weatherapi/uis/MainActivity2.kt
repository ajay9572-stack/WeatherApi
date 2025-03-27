package com.example.weatherapi.uis

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.weatherapi.R
import com.example.weatherapi.databinding.ActivityMain2Binding
import com.example.weatherapi.interfaces.ApiInterface
import com.example.weatherapi.models.Weather
import com.example.weatherapi.models.WeatherResponse
import com.example.weatherapi.models.Wind
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        with(binding) {
            fetchData()






            }

        }
    private fun fetchData(){
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://yahoo-weather5.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getWeatherResponse(
            "Toronto",
            "json",
            "metric",
            "5f4f0939ddmsh37728f0e0b39793p13e9aajsn72c861191c8c",
            "yahoo-weather5.p.rapidapi.com"

        )
        retrofitData.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: retrofit2.Response<WeatherResponse>
            ) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    if (weatherResponse != null) {
                        updateUi(weatherResponse)
                    }


                }
                else{
                    Toast.makeText(
                        this@MainActivity2,
                        "Error: ${response.code()} - ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity2,
                    "Network error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        })




    }



    private fun updateUi(weatherResponse: WeatherResponse){
        with(binding){
            tectCity.text = weatherResponse.location.city
            textDegree.text=weatherResponse.wind.direction
            textMpers.text = weatherResponse.wind.speed.toString()
            textPercentage.text = weatherResponse.atmosphere.humidity.toString()
            textNumber.text= weatherResponse.atmosphere.visibility.toString()
            textMeasure.text= weatherResponse.atmosphere.pressure.toString()
            textSubah.text= weatherResponse.astronomy.sunset
            textSam.text = weatherResponse.astronomy.sunrise
            textCelcious.text = weatherResponse.condition.temperature.toString()
            textHaze.text = weatherResponse.condition.text

            val days = listOf(binding.day1, binding.day2, binding.day3, binding.day4,
                binding.day5, binding.day6, binding.day7)
            val temps = listOf(binding.tem1, binding.tem2, binding.tem3, binding.tem4,
                binding.tem5, binding.tem6, binding.tem7)

            weatherResponse.forecasts.take(7).forEachIndexed { index, forecast ->
                days.getOrNull(index)?.text = forecast.day ?: "Day ${index + 1}"
                temps.getOrNull(index)?.text = "${forecast.high ?: "--"}°"
            }


        }
    }



    }
