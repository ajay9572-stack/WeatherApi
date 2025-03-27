package com.example.weatherapi.interfaces

import com.example.weatherapi.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun getWeatherResponse (
        @Query("location") location: String,
        @Query("format") format: String,
        @Query("u") u: String,
        @Header("x-rapidapi-key") apiKey: String,
        @Header("x-rapidapi-host") apiHost: String

        ):  Call<WeatherResponse>


    }
   
