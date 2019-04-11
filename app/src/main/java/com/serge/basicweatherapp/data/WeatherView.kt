package com.serge.basicweatherapp.data

import android.graphics.drawable.Drawable

data class WeatherView(
    var title: String,
    var temperature: String,
    var description: String,
    var bgDrawable: Drawable,
    var type: Int
)