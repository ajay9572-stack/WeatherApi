package com.example.weatherapi.uis

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.weatherapi.R
import com.example.weatherapi.databinding.ActivityMain2Binding
import com.example.weatherapi.interfaces.ApiInterface
import com.example.weatherapi.models.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val TAG = "MainActivity2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        setupSearchView()
        fetchData()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun fetchData() {
        fetchWeatherData("Toronto") // Default city
    }

    private fun fetchWeatherData(location: String) {
        // Show loading indicator
        Toast.makeText(this, "Fetching weather for $location...", Toast.LENGTH_SHORT).show()

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://yahoo-weather5.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getWeatherResponse(
            location,
            "json",
            "metric",
            "5f4f0939ddmsh37728f0e0b39793p13e9aajsn72c861191c8c",
            "yahoo-weather5.p.rapidapi.com"
        )

        Log.d(TAG, "Making API request to Yahoo Weather for $location")

        retrofitData.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    Log.d(TAG, "Response successful: ${response.code()}")

                    if (weatherResponse != null) {
                        Log.d(TAG, "Weather data received for: ${weatherResponse.location.city}")
                        updateUi(weatherResponse)
                    } else {
                        Log.e(TAG, "Response body is null")
                        Toast.makeText(
                            this@MainActivity2,
                            "Error: Empty response received",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.e(TAG, "Error response: ${response.code()} - ${response.message()}")
                    Log.e(TAG, "Response error body: ${response.errorBody()?.string()}")

                    Toast.makeText(
                        this@MainActivity2,
                        "Error: ${response.code()} - ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e(TAG, "Network failure", t)
                Toast.makeText(
                    this@MainActivity2,
                    "Network error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun updateUi(weatherResponse: WeatherResponse) {
        try {
            with(binding) {
                // Update main weather information
                textTemp.text = "${weatherResponse.currentObservation.condition.temperature}°"
                textCountry.text = weatherResponse.location.country
                textMausam.text = weatherResponse.currentObservation.condition.text
                tectCity.text = weatherResponse.location.city

                // Set high/low temperatures from the first forecast
                if (weatherResponse.forecasts.isNotEmpty()) {
                    textH.text = "H: ${weatherResponse.forecasts[0].high}°"
                    textL.text = "L: ${weatherResponse.forecasts[0].low}°"
                }

                // Update wind information
                textDegree.text = weatherResponse.currentObservation.wind.direction
                textMpers.text = weatherResponse.currentObservation.wind.speed.toString()

                // Update atmosphere information
                textPercentage.text = weatherResponse.currentObservation.atmosphere.humidity.toString()
                textNumber.text = weatherResponse.currentObservation.atmosphere.visibility.toString()
                textMeasure.text = weatherResponse.currentObservation.atmosphere.pressure.toString()

                // Update astronomy information
                textSubah.text = weatherResponse.currentObservation.astronomy.sunrise
                textSam.text = weatherResponse.currentObservation.astronomy.sunset

                // Update condition information
                textCelcious.text = weatherResponse.currentObservation.condition.temperature.toString()
                textHaze.text = weatherResponse.currentObservation.condition.text

                // Update forecast days
                val days = listOf(day1, day2, day3, day4, day5, day6, day7)
                val temps = listOf(tem1, tem2, tem3, tem4, tem5, tem6, tem7)

                weatherResponse.forecasts.take(7).forEachIndexed { index, forecast ->
                    days.getOrNull(index)?.text = forecast.day
                    temps.getOrNull(index)?.text = "${forecast.high}°"
                }
            }

            Log.d(TAG, "UI updated successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error updating UI", e)
            Toast.makeText(
                this@MainActivity2,
                "Error displaying data: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}