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
    val temp = this.optDouble("temp")
    val datetime = this.optString("datetime")
    val ts = this.optLong("ts")
    val weather = Weather(this.optJSONObject("weather").toString())
}

class Weather(json: String) : JSONObject(json) {
    val description: String? = this.optString("description")
}