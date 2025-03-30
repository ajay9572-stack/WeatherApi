package com.example.weatherapi.models

import java.io.Serializable
import java.util.Date
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val location: Location,
    @SerializedName("current_observation")
    val currentObservation: CurrentObservation,
    val forecasts: List<Forecast>,
) : Serializable

data class Location(
    val city: String,
    val woeid: Int,
    val country: String,
    val lat: Double,
    val long: Double,
    @SerializedName("timezone_id")
    val timezoneId: String
) : Serializable

data class Wind(
    val chill: Int,
    val direction: String,
    val speed: Int,
) : Serializable

data class Atmosphere(
    val humidity: Int,
    val visibility: Double,
    val pressure: Double
) : Serializable

data class Astronomy(
    val sunrise: String,
    val sunset: String
) : Serializable

data class Condition(
    val temperature: Int,
    val text: String,
    val code: Int
) : Serializable

data class CurrentObservation(
    @SerializedName("pubDate")
    val pubDate: Long,
    val wind: Wind,
    val atmosphere: Atmosphere,
    val astronomy: Astronomy,
    val condition: Condition
) : Serializable

data class Forecast(
    val day: String,
    val date: Long,
    val high: Int,
    val low: Int,
    val text: String,
    val code: Int
) : Serializable