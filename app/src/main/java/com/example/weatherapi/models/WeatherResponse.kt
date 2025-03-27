package com.example.weatherapi.models

import java.io.Serializable
import java.util.Date

data class WeatherResponse(
    val location: Location,
    val currentObservation: CurrentObservation,
    val forecasts: List<Forecast>,
    val wind: Wind,
    val atmosphere: Atmosphere,
    val astronomy: Astronomy,
    val condition: Condition,
) : Serializable

data class Location(
    val city: String,
    val woeid: Int,
    val country: String,
    val lat: Double,
    val long: Double,
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
    val pubDate: Date,
    val wind: Wind,
    val atmosphere: Atmosphere,
    val astronomy: Astronomy,
    val condition: Condition
) : Serializable {
    constructor(
        pubDate: Long,
        wind: Wind,
        atmosphere: Atmosphere,
        astronomy: Astronomy,
        condition: Condition
    ) : this(Date(pubDate * 1000), wind, atmosphere, astronomy, condition)
}

data class Forecast(
    val day: String,
    val date: Date,
    val high: Int,
    val low: Int,
    val text: String,
    val code: Int
) : Serializable {
    constructor(
        day: String,
        date: Long,
        high: Int,
        low: Int,
        text: String,
        code: Int
    ) : this(day, Date(date * 1000), high, low, text, code)
}