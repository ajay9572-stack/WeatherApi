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
            // These two fields may not be updating correctly
            textTemp.text = "${weatherResponse.condition.temperature}°"
            textCountry.text = weatherResponse.location.country

            // Update the weather condition text
            textMausam.text = weatherResponse.condition.text

            // Set high/low temperatures from the first forecast
            if (weatherResponse.forecasts.isNotEmpty()) {
//                text_hl.text = "H: ${weatherResponse.forecasts[0].high}° L: ${weatherResponse.forecasts[0].low}°"
                textH.text = "H: ${weatherResponse.forecasts[0].high}°"
                textL.text = "L: ${weatherResponse.forecasts[0].low}°"
            }

            // The rest of your code looks correct for these fields
            tectCity.text = weatherResponse.location.city
            textDegree.text = weatherResponse.wind.direction
            textMpers.text = weatherResponse.wind.speed.toString()
            textPercentage.text = weatherResponse.atmosphere.humidity.toString()
            textNumber.text = weatherResponse.atmosphere.visibility.toString()
            textMeasure.text = weatherResponse.atmosphere.pressure.toString()
            textSubah.text = weatherResponse.astronomy.sunset
            textSam.text = weatherResponse.astronomy.sunrise
            textCelcious.text = weatherResponse.condition.temperature.toString()
            textHaze.text = weatherResponse.condition.text

            val days = listOf(day1, day2, day3, day4, day5, day6, day7)
            val temps = listOf(tem1, tem2, tem3, tem4, tem5, tem6, tem7)

            weatherResponse.forecasts.take(7).forEachIndexed { index, forecast ->
                days.getOrNull(index)?.text = forecast.day ?: "Day ${index + 1}"
                temps.getOrNull(index)?.text = "${forecast.high ?: "--"}°"
            }
        }
    }



    }
