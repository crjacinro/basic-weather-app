package com.serge.basicweatherapp.data

import org.json.JSONObject

class WeatherApiResponse(json: String) : JSONObject(json) {
    val city_name: String? = this.optString("city_name")
    val data = this.optJSONArray("data")
        ?.let {
            0.until(it.length()).map { i -> it.optJSONObject(i) }
        }?.map {
            WeatherData(it.toString())
        }
}

class WeatherData(json: String) : JSONObject(json) {
    val app_max_temp = this.optDouble("app_max_temp")
    val app_min_temp = this.optDouble("app_min_temp")
}