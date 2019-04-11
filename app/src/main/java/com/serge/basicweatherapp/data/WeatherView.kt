package com.serge.basicweatherapp.data

import android.graphics.drawable.Drawable

data class WeatherView(
    var location: String,
    var featureTitle: String,
    var forecastTitle: String,
    var temperature: String,
    var description: String,
    var bgDrawable: Drawable,
    var type: Int,
    var ts: Long,
    var wind_speed: Double,
    var wind_direction: String,
    var pressure: Double
)